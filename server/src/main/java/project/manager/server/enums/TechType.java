package project.manager.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechType {
    FRONT("Back-End"),
    BACK("Front-End"),
    AI("AI"),
    ETC("기타");

    private final String label;
}
