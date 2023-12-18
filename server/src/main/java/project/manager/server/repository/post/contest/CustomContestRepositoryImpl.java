package project.manager.server.repository.post.contest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import project.manager.server.domain.post.contest.ContestPost;

public class CustomContestRepositoryImpl implements CustomContestRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ContestPost> searchContestPostList(
            List<Long> scales,
            List<Long> categories,
            List<Long> organizations,
            List<Long> targets,
            List<Long> benefits,
            String keyWord,
            LocalDate today,
            Pageable pageInfo) {

        StringJoiner joinClause = new StringJoiner(" LEFT JOIN ");
        StringJoiner countJoinClause = new StringJoiner(" LEFT JOIN ");
        joinClause.add("SELECT c FROM ContestPost c ");
        countJoinClause.add("SELECT COUNT(c) FROM ContestPost c ");

        StringJoiner whereClause = new StringJoiner(" AND ");

        //JOIN FETCH절과 WHERE절
        if (scales != null && !scales.isEmpty()) {
            joinClause.add(" c.scale s ");
            countJoinClause.add(" c.scale s ");
            whereClause.add(" s.id IN :scales ");
        } if (categories != null && !categories.isEmpty()) {
            joinClause.add(" c.category ca ");
            countJoinClause.add(" c.category ca ");
            whereClause.add(" ca.id IN :categories ");
        } if (organizations != null && !organizations.isEmpty()) {
            joinClause.add(" c.organization o ");
            countJoinClause.add(" c.organization o ");
            whereClause.add(" o.id IN :organizations ");
        } if (targets != null && !targets.isEmpty()) {
            joinClause.add(" c.target t ");
            countJoinClause.add(" c.target t ");
            whereClause.add(" t.id IN :targets ");
        } if (benefits != null && !benefits.isEmpty()) {
            joinClause.add(" c.benefit b ");
            countJoinClause.add(" c.benefit b ");
            whereClause.add(" b.id IN :benefits ");
        }

        //LIKE절
        if (keyWord != null && !keyWord.isEmpty()) {
            String[] parts = keyWord.split("\\s+");
            if (parts.length != 0) {
                StringJoiner searchClause = new StringJoiner(" OR ");

                for(String part : parts) {
                    searchClause.add(" c.title LIKE '%" + part + "%' ");
                    searchClause.add(" c.content LIKE '%" + part + "%' ");
                }

                whereClause.add("(" + searchClause.toString() + ")");
            }
        }
        String tmp = whereClause.toString();
        if (!(tmp == null || tmp.isEmpty())) {
            tmp = " WHERE " + tmp;
        }

        //ORDER BY 절
        String orderClause = " ORDER BY CASE WHEN (c.startAt <= :today AND c.endAt >= :today) " +
                "THEN 1 ELSE 0 END DESC, c.startAt DESC";
        String jpql = joinClause.toString() + tmp + orderClause;
        String countJpql = countJoinClause.toString() + tmp + orderClause;

        TypedQuery<ContestPost> query = entityManager.createQuery(jpql, ContestPost.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);

        //파라미터 바인딩
        if (scales != null && !scales.isEmpty()) {
            query.setParameter("scales", scales);
            countQuery.setParameter("scales", scales);
        } if (categories != null && !categories.isEmpty()) {
            query.setParameter("categories", categories);
            countQuery.setParameter("categories", categories);
        } if (organizations != null && !organizations.isEmpty()) {
            query.setParameter("organizations", organizations);
            countQuery.setParameter("organizations", organizations);
        } if (targets != null && !targets.isEmpty()) {
            query.setParameter("targets", targets);
            countQuery.setParameter("targets", targets);
        } if (benefits != null && !benefits.isEmpty()) {
            query.setParameter("benefits", benefits);
            countQuery.setParameter("benefits", benefits);
        }
        query.setParameter("today", today);
        countQuery.setParameter("today",today);

        //페이징 처리
        query.setFirstResult((int) pageInfo.getOffset());
        query.setMaxResults(pageInfo.getPageSize());

        List<ContestPost> content = query.getResultList();
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(content, pageInfo, total);
    }
}
