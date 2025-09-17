package org.shark.boot05.board.service;

import java.util.List;
import java.util.Map;

import org.shark.boot05.board.dto.BoardDTO;
import org.shark.boot05.board.mapper.BoardMapper;
import org.shark.boot05.common.dto.PageDTO;
import org.shark.boot05.common.util.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

	private final BoardMapper boardMapper;
	private final PageUtil pageUtil;
	
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getBoardList(PageDTO dto, String sort) {
		int totalItem = boardMapper.selectBoardCount();
		dto.setTotalItem(totalItem);
		pageUtil.calculatePaging(dto);
		List<BoardDTO> boardList = boardMapper.selectBoardList(Map.of("size", dto.getSize(), "offset", dto.getOffset(), "sort", sort));
		return Map.of("boardList", boardList, "pageDTO", dto);
	}
	
	@Transactional(readOnly = true)
	@Override
	public BoardDTO getBoardById(Long bid) {
		return boardMapper.selectBoardById(bid);
	}

	@Override
	public Boolean insertBoard(BoardDTO board) {
	    int insertBid = boardMapper.insertBoard(board);
	    if (insertBid > 0) {
	    	return true;
	    }
	    else {
	    	return false;
	    }
	}

	@Override
	public Boolean updateBoard(BoardDTO board) {
		int updateBid = boardMapper.updateBoard(board);
	    if (updateBid > 0) {
	    	return true;
	    }
	    else {
	    	return false;
	    }
	}

	@Override
	public Boolean deleteBoard(Long bid) {
		return boardMapper.deleteBoard(bid) > 0;
	}

	

}
