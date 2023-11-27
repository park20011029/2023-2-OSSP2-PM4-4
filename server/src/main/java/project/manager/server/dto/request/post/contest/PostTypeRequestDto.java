package project.manager.server.dto.request.post.contest;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class PostTypeRequestDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BenefitDto {

        @NotNull
        private String id;
        @NotNull
        private String benefit;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDto {

        @NotNull
        private String id;
        @NotNull
        private String category;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScaleDto {

        @NotNull
        private String id;
        @NotNull
        private String scale;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TargetDto {

        @NotNull
        private String id;
        @NotNull
        private String target;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrganizationDto {

        @NotNull
        private String id;
        @NotNull
        private String organization;
    }
}
