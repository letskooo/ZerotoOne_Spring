package pm2_5.studypartner.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pm2_5.studypartner.util.OpenaiUtil;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MultipleService {

    private final OpenaiUtil openaiUtil;
}
