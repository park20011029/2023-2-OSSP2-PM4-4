package project.manager.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartState {
    APPROVAL("승인"),
    REFUSAL("거절"),
    STANDBY("대기");

    private final String toKorean;
}
