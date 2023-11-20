package com.example.tgbottocourseproject.repository.qustion;

import com.example.tgbottocourseproject.models.questions.GroupQuestion;
import com.example.tgbottocourseproject.models.questions.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupQuestionRepository extends CrudRepository<GroupQuestion, Long> {
//    @Query("SELECT new OrganizationCount(o, COUNT(sv)) " +
//            "FROM Organization o JOIN o.members m " +
//            "LEFT JOIN o.videos v " +
//            "LEFT JOIN v.shownVideos sv " +
//            "WHERE m = :member " +
//            "GROUP BY o " +
//            "ORDER BY o.title")

//    @Query("SELECT q.id,q.name_question,q.number_question,q.type_question FROM question q" +
//            "left join group_question_question_list gqql on q.id = question_list_id" +
//            "left join group_question gq on gqql.group_question_id = gq.id " +
//            "where gq.id = :id and q.number_question = :numberOfQuestion")
//    @Query("select q.id,q.name_question,q.number_question,q.type_question from group_question gq \n" +
//            "left join group_question_question_list gqql on id = group_question_id\n" +
//            "left join question q on question_list_id = q.id\n" +
//            "where gq.id = :id and q.number_question = :numberOfQuestion")
//    public Question findQuestion(@Param(value = "id") Long groupOfQuestion, @Param(value = "numberOfQuestion") Long numberOfQuestion);


//    @Query("select count (q) from GroupQuestion q")
//    Long countOf();
//
//    @Query("select g.questionList from GroupQuestion g")
//    List<Question> getGroupQuestionsBy();

    @Query("select g from GroupQuestion g")
    List<GroupQuestion> getAllQ();
}
