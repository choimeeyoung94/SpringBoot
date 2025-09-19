package org.shark.boot05.board.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import org.shark.boot05.board.dto.BoardDTO;
import org.shark.boot05.board.service.BoardService;
import org.shark.boot05.common.dto.PageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/*
 * 홈화면   GET     /  
 * 목록    GET     /board/list?page=1&size=20&sort=DESC
 * 등록폼   GET     /board/write
 * 등록    POST    /board/write
 * 상세    GET     /board/detail?bid=1
 * 수정폼  GET      /board/edit?bid=1
 * 수정   POST      /board/update
 * 삭제   POST     /board/delete
 * */

@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardService boardService;

	@GetMapping("/")
	public String home() {
		return "home"; // SpringResourceTemplatesResolver에 의해서 prefix, suffix가 자동으로 추가된다
		               // prefix="/templates/"
		               // suffix=".html"
	}
	
	
	@GetMapping("/board/list")
	public String list(
			PageDTO dto,
			@RequestParam(value = "sort", defaultValue = "DESC") String sort,
			Model model) {
		if (sort.isEmpty() || (!sort.equalsIgnoreCase("asc") && !sort.equalsIgnoreCase("desc"))) {
			sort = "DESC";
		}
		
		Map<String, Object> result = boardService.getBoardList(dto, sort);
		model.addAttribute("boardList", result.get("boardList"));	
		model.addAttribute("pageDTO", result.get("pageDTO"));		
		return "board/list";
	}
	
	@GetMapping("/board/detail")
	public String detail(@RequestParam(value = "bid", required = false) Long bid, Model model) {
		if (bid == null) {
			throw new IllegalArgumentException("게시글 ID 정보를 확인할 수 없습니다.");
		}
		
		if (bid <= 0) {
			throw new IllegalArgumentException("게시글 ID는 1 이상의 정수 입니다.");
		}
		
		BoardDTO foundBoard = boardService.getBoardById(bid);
		if (foundBoard == null) {
			throw new NoSuchElementException("게시글 ID가 " + bid + "인 게시글이 존재하지 않습니다.");
		}
		model.addAttribute("board", foundBoard);
		return "board/detail";
	}
	
	
	@GetMapping("/board/create")
	public String writeForm(@ModelAttribute(value="board") BoardDTO board) {
		return "board/writeForm";
	}
	

	@PostMapping("/board/create")
	public String create(
			@Valid BoardDTO board, // BoardDTO 유효성 검사를 한다
			BindingResult bindingResult, // @Valid 검증 결과를 저장한다
			Model model, 
			RedirectAttributes redirectAttributes) {
		// 유효성 검사를 통과하지 못한 경우 writeForm으로 되돌아 간다
		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult);
			model.addAttribute("board", board);
			return "board/writeForm";
		}
		// 등록 시 오류가 발생하면 writeFor으로 리다이렉트한다
		try {
			boolean success = boardService.insertBoard(board);
			redirectAttributes.addFlashAttribute("msg",success ? "등록 성공" : "등록 실패");
			return "redirect:/board/list";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("insertError", "게시글 등록 중 오류가 발생했습니다.")
			.addFlashAttribute("board", board);
			return "redirect:/board/write";
		}
	
		
	}
	
	@GetMapping("/board/edit")
	public String editForm(@RequestParam("bid") Long bid, Model model) {
	    BoardDTO board = boardService.getBoardById(bid);
	    model.addAttribute("board", board);
	    return "board/edit";
	}
	
	@PostMapping("/board/update")
	public String update(@Valid BoardDTO board, 
						BindingResult bindingResult, // @Valid 검증 결과를 저장한다
			            Model model    , RedirectAttributes redirectAttributes) {
		
		// 유효성 검사를 통과하지 못한 경우 writeForm으로 되돌아 간다
			if (bindingResult.hasErrors()) {
				model.addAttribute("errors", bindingResult);
				model.addAttribute("board", board);
				return "board/edit";
			}
		
		if (boardService.updateBoard(board)) {
			redirectAttributes.addFlashAttribute("msg", "수정 성공").addAttribute("bid", board.getBid()); //?bid= + board.getBid();
			return "redirect:/board/list";
		} else {
			redirectAttributes.addFlashAttribute("msg", "수정 실패");
			return "redirect:/board/list";
		}
				
	}
	
	
	@PostMapping("/board/delete")
	public String delete(@RequestParam("bid") Long bid, RedirectAttributes redirectAttributes) {
		if (boardService.deleteBoard(bid)) {
			redirectAttributes.addFlashAttribute("msg", "삭제 성공");
		} else {
			redirectAttributes.addFlashAttribute("msg", "삭제 실패");
		}
		return "redirect:/board/list";
	}
	
	
}
