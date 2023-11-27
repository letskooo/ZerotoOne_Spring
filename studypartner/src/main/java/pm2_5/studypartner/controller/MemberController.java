package pm2_5.studypartner.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.dto.auth.MemberJoinDTO;
import pm2_5.studypartner.error.ApiException;
import pm2_5.studypartner.error.ErrorResponse;
import pm2_5.studypartner.error.member.MemberErrorStatus;
import pm2_5.studypartner.service.MemberService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/checkUsername/{username}")
    public ResponseEntity<String> checkIdPOST(@PathVariable String username){

        if (memberService.checkIdDuplicate(username) == "exist"){

            return new ResponseEntity<>("exist", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("available", HttpStatus.OK);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<MemberJoinDTO> signUpMember(
            @Valid @RequestBody MemberJoinDTO memberJoinDTO,
            BindingResult result) throws ApiException {

        List<String> messages = new ArrayList<>();
        if (result.hasErrors()){
            result.getAllErrors().stream().forEach(objectError -> messages.add(objectError.getDefaultMessage()));

            throw new ApiException(MemberErrorStatus.VALIDATION_ERROR, StringUtils.join(messages, ','));
        }

        memberService.joinMember(memberJoinDTO);
        return new ResponseEntity<>(memberJoinDTO, HttpStatus.OK);
    }
}
