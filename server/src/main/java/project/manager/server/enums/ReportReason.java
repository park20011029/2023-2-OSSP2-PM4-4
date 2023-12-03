package project.manager.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportReason {
//    부적절한 언어: "Inappropriate Language"
//    허위 정보 의심: "Suspected False Information"
//    같은 내용 반복: "Repetitive Content"
    INAPPROPRIATE("부적절한 언어"),
    FALSE_INFORMATION("허위 정보 의심"),
    REPETITIVE("같은 내용 반복"),
    ETC("기타");

    private final String message;
}
