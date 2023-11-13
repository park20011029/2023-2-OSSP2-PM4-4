package project.manager.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SchoolRegister {
    ENROLLED("재학"),
    GRADUATE("졸업"),
    DROPOUT("중퇴");

    private final String toKorean;
}
