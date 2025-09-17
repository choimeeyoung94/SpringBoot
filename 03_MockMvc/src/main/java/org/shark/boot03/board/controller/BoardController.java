package org.shark.boot03.board.controller;

import org.shark.boot03.board.dto.BoardDTO;
import org.shark.boot03.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("boardList", boardService.getBoardList());
		return "board/list";
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam(value = "bid") Long bid, Model model) {
		model.addAttribute("board", boardService.getBoardById(bid));
		return "board/detail";
	}

	@PostMapping("/write")
	public String write(BoardDTO board, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("msg", boardService.insertBoard(board) ? "등록 성공" : "등록 실패");
		return "redirect:/board/list";
	}
	
	@PostMapping("/update")
	public String update(BoardDTO board, RedirectAttributes redirectAttributes) {
		if (boardService.updateBoard(board)) {
			redirectAttributes.addFlashAttribute("msg", "수정 성공").addAttribute("bid", board.getBid()); //?bid= + board.getBid();
			return "redirect:/board/detail";
		} else {
			redirectAttributes.addFlashAttribute("msg", "수정 실패");
			return "redirect:/board/list";
		}
				
	}
	
	
	@PostMapping("/delete")
	public String delete(Long bid, RedirectAttributes redirectAttributes) {
		if (boardService.deleteBoard(bid)) {
			redirectAttributes.addFlashAttribute("msg", "삭제 성공");
		} else {
			redirectAttributes.addFlashAttribute("msg", "삭제 실패");
		}
		return "redirect:/board/list";
	}
	
	
}
