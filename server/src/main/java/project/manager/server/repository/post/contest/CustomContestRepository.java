package project.manager.server.repository.post.contest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import project.manager.server.domain.post.contest.ContestPost;

public interface CustomContestRepository {
    Page<ContestPost> searchContestPostList(List<Long> scales,
                                            List<Long> categories,
                                            List<Long> organizations,
                                            List<Long> targets,
                                            List<Long> benefits,
                                            String keyWord,
                                            LocalDate today,
                                            Pageable pageInfo);
}
