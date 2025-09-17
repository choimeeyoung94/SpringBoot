package org.shark.boot02;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.shark.boot02.board.dto.BoardDTO;
import org.shark.boot02.mapper.BoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTests extends AbstractTestNGSpringContextTests {

	  @Autowired
	  private BoardMapper boardMapper;

	  private static final Logger log = LoggerFactory.getLogger(ApplicationTests.class);

	  @Test(description = "컨텍스트 로드 테스트", priority = 0)
	  public void contextLoads() {
	    assertNotNull(boardMapper);
	    log.info("컨텍스트 로드 테스트 종료");
	  }

	  @Test(description = "현재 시간 가져오기 테스트", priority = 1, dependsOnMethods = "contextLoads")
	  public void nowTest() {
	    log.info("NOW: {}", boardMapper.now());
	  }

	  @Test(description = "게시판 목록 가져오기 테스트", priority = 2, dependsOnMethods = "contextLoads")
	  public void boardListTest() {
	    assertEquals(boardMapper.selectBoardList().size(), 1);
	  }

	  @Test(description = "게시판 상세 테스트", priority = 3, dependsOnMethods = "boardListTest")
	  public void boardDetailTest() {
	    Long bid = 1L;
	    // selectBordById가 BoardDTO를 반환한다고 가정
	    assertEquals(boardMapper.selectBordById(bid).getTitle(), "테스트 제목");
	  }

	  @Test(description = "신규 게시글 등록 테스트", priority = 4, dependsOnMethods = "contextLoads")
	  public void boardInsertTest() {
	    BoardDTO board = new BoardDTO(null, "신규 제목", "신규 내용", null);
	    assertEquals(boardMapper.insertBoard(board), 1);
	    assertEquals(boardMapper.selectBordById(2L).getTitle(), "신규 제목");
	    log.info("등록된 created_at: {}", boardMapper.selectBordById(2L).getCreatedAt());
	  }

	  @Test(description = "게시글 수정 테스트", priority = 5, dependsOnMethods = "boardInsertTest")
	  public void boardUpdateTest() {
	    Long bid = 1L;
	    String title = "수정 제목";
	    boardMapper.updateBoard(new BoardDTO(bid, title, null, null));
	    assertEquals(boardMapper.selectBordById(bid).getTitle(), title);
	    assertNull(boardMapper.selectBordById(bid).getContent());
	  }

	  @Test(description = "게시글 삭제 테스트", priority = 6, dependsOnMethods = "boardUpdateTest")
	  public void boardDeleteTest() {
	    Long bid = 1L;
	    boardMapper.deleteBoard(bid);
	    assertNull(boardMapper.selectBordById(bid));
	  }

}
