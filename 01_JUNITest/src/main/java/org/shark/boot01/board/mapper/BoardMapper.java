package org.shark.boot01.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.shark.boot01.board.dto.BoardDTO;

/*
 * MyBatis Interface 방식
 * 
 * 
 * */

/*
 * Mapper Interface 등록 방법
 * 
 * 1. @Mapper
 *    1) 하나의 Mapper Interface를 등록한다
 *    2) Mapper Interface마다 추가한다
 * 2. @MapperScan
 *    1) 여러 Mapper Intergace를 등록한다
 *    2) @Configuration 클래스에 패키지를 등록
 * 
 * 
 * 
 * */



@Mapper
public interface BoardMapper {

	@Select(value = "SELECT NOW()")
	String now();

	@Select(value = "SELECT bid, title, content, created_at FROM tbl_board ORDER BY bid DESC")
	List<BoardDTO> selectBoardList();
	
	@Select (value = "SELECT bid, title, content, created_at FROM tbl_board WHERE bid = #{bid}")
	BoardDTO  selectBordById(@Param(value = "bid") Long bid);
	
	@Insert(value = "INSERT INTO tbl_board VALUES(null, #{title}, #{content}, NOW())")
	int insertBoard(BoardDTO board);
	
	@Update(value ="UPDATE tbl_board SET title = #{title}, content = #{content} WHERE bid=  #{bid}")
	int updateBoard(BoardDTO board);
	
	@Delete(value = "DELETE FROM tbl_board where bid = #{bid}")
	int deleteBoard(@Param(value = "bid") Long bid);
	
}
