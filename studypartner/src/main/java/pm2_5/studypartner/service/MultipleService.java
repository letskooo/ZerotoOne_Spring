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
import pm2_5.studypartner.domain.MultipleChoice;
import pm2_5.studypartner.dto.multiple.MultipleDTO;
import pm2_5.studypartner.dto.multiple.MultipleRespDTO;
import pm2_5.studypartner.dto.multiple.MultiplesFindDTO;
import pm2_5.studypartner.dto.papago.TextTransReqDTO;
import pm2_5.studypartner.repository.DocumentRepository;
import pm2_5.studypartner.repository.MultipleChoiceRepository;
import pm2_5.studypartner.repository.MultipleRepository;
import pm2_5.studypartner.util.NaverCloudUtil;
import pm2_5.studypartner.util.OpenaiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MultipleService {

    public final NaverCloudUtil naverCloudUtil;
    public final OpenaiUtil openaiUtil;
    public final DocumentRepository documentRepository;
    public final MultipleRepository multipleRepository;
    public final MultipleChoiceRepository multipleChoiceRepository;

    // 객관식 전체 조회
    public List<MultiplesFindDTO> findMultipleList(Long documentId) {

        List<Multiple> multiples = multipleRepository.findByDocumentId(documentId);

        List<MultiplesFindDTO> multipleDTOList = multiples.stream()
                .map(multiple -> new MultiplesFindDTO(multiple.getId(),
                        multiple.getTitle(), multiple.getCreated().toLocalDate()))
                .collect(Collectors.toList());

        return multipleDTOList;
    }

    // 객관식 등록
    public MultipleRespDTO registerMultiple(Long documentId) throws JsonProcessingException {

        // 해당 자료를 가져옴
        Document findDocument = documentRepository.findById(documentId).get();
        String translatedText = findDocument.getEnContent();

        // 역할 설정
        String system = """
                    You are to create a multiple choice question by extracting what is important from the material I send you.
                    I'll tell you the steps, and you'll follow them.
                    1. look at the material and select a concept or keyword that you think is important.  This will have key title in JSON sentences.
                    2. The purpose of the view is to test your knowledge of those concepts. Provide examples of concepts that might be confused with those concepts, or terms that you think are similar, as multiple choice questions. This will have key content in JSON sentences.
                    3. Write a description of that question. For example, choose the wrong one, choose the right one, True or false questions are fine. etc. If there are multiple correct answer choices, make sure to include the phrase Choose all of them in your description. This will have key question in JSON sentences.
                    4. If there are any concepts left that you think are important, please create another question.
                    5. Provide the correct answer to the question. This will have key answer in JSON sentences.
                    6. Please write JSON-style responses so that Jackson can paraphrase them by linking them with the summary. I'll show you an example of a response format in ```.
                      ```
                      [
                          {
                              "title": "Pix2pix structure",
                              "question": "Pix2pix employs a unique structure in comparison to traditional GANs. Considering the given information, which of the following statements are true about the structure and functioning of the Pix2pix model?",
                              "multipleChoices": [
                                  {
                                      "content": "Pix2pix uses an input image and a ground truth image to generate its output.",
                                      "number": 1
                                      "answer": true
                                  },
                                  {
                                      "content": "Pix2pix uses max pooling to reduce width and height on the base.",
                                      "number": 2
                                      "answer": false
                                  },
                                  {
                                      "content": "Pix2pix uses a variant of ConvNet structure called U-Net for generating the output image.",
                                      "number": 3
                                      "answer": true
                                  },
                                  {
                                      "content": "The encoder part of the Pix2pix model contains only 4 blocks.",
                                      "number": 4
                                      "answer": false
                                  }
                              ]
                          },
                          // Add more JSON responses if needed
                      ]
                      ```
                """;

        // chat gpt의 응답을 추출
        String json = openaiUtil.extractContent(system, translatedText, true);

        // chat gpt의 응답을 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        List<MultipleDTO> responses = objectMapper.readValue(json, new TypeReference<List<MultipleDTO>>() {});

        List<Long> idList = new ArrayList<>();
        for(MultipleDTO multipleDTO : responses){
            multipleDTO.setDocumentId(documentId);

            TextTransReqDTO textTransReqDTO = new TextTransReqDTO(documentId, "en", "ko", multipleDTO.getTitle());
            String translateTitle = naverCloudUtil.translateText(textTransReqDTO);

            textTransReqDTO.setText(multipleDTO.getQuestion());
            String translatedQuestion = naverCloudUtil.translateText(textTransReqDTO);

            Multiple newMultiple = new Multiple(findDocument ,translateTitle, translatedQuestion);
            newMultiple = multipleRepository.save(newMultiple);
            idList.add(newMultiple.getId());

            // 각 keyword를 확인 및 저장
            for(MultipleDTO.ChoicesDTO choice: multipleDTO.getMultipleChoices()) {
                // 키워드와 설명 번역
                textTransReqDTO.setText(choice.getContent());
                String translateContent = naverCloudUtil.translateText(textTransReqDTO);

                MultipleChoice newMultipleChoice = new MultipleChoice(newMultiple, choice.getNumber(), translateContent, choice.getAnswer());
                multipleChoiceRepository.save(newMultipleChoice);
            }
        }


        return new MultipleRespDTO(documentId, idList);
    }

    public List<MultipleDTO> findRegisteredMultiples(Long documentId, List<Long> multipleIds){
        List<MultipleDTO> multipleDTOList = new ArrayList<>();
        for(Long i : multipleIds){
            Multiple multiple = multipleRepository.findById(i).get();

            List<MultipleDTO.ChoicesDTO> choicesDTOList = multiple.getMultipleChoices().stream()
                    .map(choice -> new MultipleDTO.ChoicesDTO(choice.getContent(), choice.getNumber(), choice.getAnswer()))
                    .collect(Collectors.toList());
            MultipleDTO multipleDTO = new MultipleDTO(multiple.getDocument().getId(), multiple.getTitle(), multiple.getQuestion(), choicesDTOList);
            multipleDTOList.add(multipleDTO);
        }

        return multipleDTOList;
    }

    public void deleteRegisteredMultiples(MultipleRespDTO multipleRespDTO){
        for(Long i : multipleRespDTO.getMultipleIds()){
            multipleRepository.deleteById(i);
        }
    }

    public MultipleDTO findMultiple(Long documentId, Long multipleId){
        Multiple multiple = multipleRepository.findById(multipleId).get();

        List<MultipleDTO.ChoicesDTO> choicesDTOList = multiple.getMultipleChoices().stream()
                .map(choice -> new MultipleDTO.ChoicesDTO(choice.getContent(), choice.getNumber(), choice.getAnswer()))
                .collect(Collectors.toList());
        return new MultipleDTO(documentId, multiple.getTitle(), multiple.getQuestion(), choicesDTOList);
    }

    public void deleteMultiple(Long documentId, Long multipleId){
        multipleRepository.deleteById(multipleId);
    }
}
