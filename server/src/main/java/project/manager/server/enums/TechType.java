package project.manager.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechType {
    FRONT("Front-End"),
    BACK("Back-End"),
    AI("AI"),
    ETC("기타");

    private final String label;
}
