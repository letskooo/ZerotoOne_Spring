package pm2_5.studypartner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.domain.Multiple;
import pm2_5.studypartner.domain.Written;
import pm2_5.studypartner.dto.multiple.MultiplesFindDTO;
import pm2_5.studypartner.dto.papago.TextTransReqDTO;
import pm2_5.studypartner.dto.written.WrittenDTO;
import pm2_5.studypartner.dto.written.WrittenRespDTO;
import pm2_5.studypartner.dto.written.WrittensFindDTO;
import pm2_5.studypartner.repository.DocumentRepository;
import pm2_5.studypartner.repository.WrittenRepository;
import pm2_5.studypartner.util.NaverCloudUtil;
import pm2_5.studypartner.util.OpenaiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WrittenService {

    public final NaverCloudUtil naverCloudUtil;
    public final OpenaiUtil openaiUtil;
    public final DocumentRepository documentRepository;
    public final WrittenRepository writtenRepository;

    // 객관식 전체 조회
    public List<WrittensFindDTO> findWrittenList(Long documentId) {

        List<Written> writtens = writtenRepository.findByDocumentId(documentId);

        List<WrittensFindDTO> writtenDTOList = writtens.stream()
                .map(written -> new WrittensFindDTO(written.getId(),
                        written.getTitle(), written.getCreated().toLocalDate()))
                .collect(Collectors.toList());

        return writtenDTOList;
    }

    public WrittenRespDTO registerWritten(Long documentId) throws JsonProcessingException {

        // 해당 자료를 가져옴
        Document findDocument = documentRepository.findById(documentId).get();
        String translatedText = findDocument.getEnContent();

        // 역할 설정
        String system = """
                   You need to create an essay question by extracting important information from the materials I send you.
                   I'll give you the steps and you can follow them.
                   1. look at the material and select a concept or keyword that you think is important. This will have key title in JSON sentences.
                   2. Write a question about the selected concept. You should create questions to see if users can explain the concept logically. Short answer questions are fine. This will have key question in JSON sentences.
                   3. If there are any concepts left that you think are important, create another question.
                   4. Write the correct answer for each question. This will have key answer in JSON sentences.
                   5. Please write JSON-style responses so that Jackson can paraphrase them by linking them with the summary. I'll show you an example of a response format in ```.
                   
                   ```
                   [
                       {
                           "title": "Pix2pix structure and functioning",
                           "question": "Why pix2pix generator uses skip connections",
                           "answer" : "Skip connections help the decoder learn details from the encoder directly and Skip connections the encoder learn from more gradients flowing from decoder"
                       },
                       // Add more JSON responses if needed
                   ]
                   ```
                """;

        // chat gpt의 응답을 추출
        String json = openaiUtil.extractContent(system, translatedText, true);

        // chat gpt의 응답을 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        List<WrittenDTO> responses = objectMapper.readValue(json, new TypeReference<List<WrittenDTO>>() {});

        List<Long> idList = new ArrayList<>();
        for(WrittenDTO writtenDTO : responses){
            writtenDTO.setDocumentId(documentId);

            TextTransReqDTO textTransReqDTO = new TextTransReqDTO(documentId, "en", "ko", writtenDTO.getTitle());
            String translateTitle = naverCloudUtil.translateText(textTransReqDTO);

            textTransReqDTO.setText(writtenDTO.getQuestion());
            String translatedQuestion = naverCloudUtil.translateText(textTransReqDTO);

            textTransReqDTO.setText(writtenDTO.getAnswer());
            String translatedAnswer = naverCloudUtil.translateText(textTransReqDTO);

            Written newWritten = new Written(findDocument ,translateTitle, translatedQuestion, translatedAnswer);
            newWritten = writtenRepository.save(newWritten);
            idList.add(newWritten.getId());
        }

        return new WrittenRespDTO(documentId, idList);
    }

    public List<WrittenDTO> findRegisteredWrittens(Long documentId, List<Long> writtenIds){
        List<WrittenDTO> writtenDTOList = new ArrayList<>();
        for(Long i : writtenIds){
            Written written = writtenRepository.findById(i).get();
            WrittenDTO writtenDTO = new WrittenDTO(written.getDocument().getId(), written.getTitle(), written.getQuestion(), written.getAnswer());
            writtenDTOList.add(writtenDTO);
        }

        return writtenDTOList;
    }

    public void deleteRegisteredWrittens(WrittenRespDTO writtenRespDTO){
        for(Long i : writtenRespDTO.getWrittenIds()){
            writtenRepository.deleteById(i);
        }
    }

    public WrittenDTO findWritten(Long documentId, Long writtenId){
        Written written = writtenRepository.findById(writtenId).get();

        return new WrittenDTO(documentId, written.getTitle(), written.getQuestion(), written.getAnswer());
    }

    public void deleteWritten(Long documentId, Long writtenId){
        writtenRepository.deleteById(writtenId);
    }

}
