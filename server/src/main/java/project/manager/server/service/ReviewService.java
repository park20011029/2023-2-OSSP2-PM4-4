package project.manager.server.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.manager.server.repository.ReviewRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

//    public Boolean createReview(Long reviewerId, Long buildingPostId, ReviewRequestDto reviewRequestDto) {
//
//
//    }

}
