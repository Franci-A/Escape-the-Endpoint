package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoApplication {

  List<String> forthClue =
      new ArrayList<String>(
          List.of(
              "0, true",
              "1, true",
              "0, false",
              "0, true",
              "1, true",
              "1, false",
              "0, false",
              "0, true",
              "0, true",
              "1, false",
              "0, true",
              "1, true",
              "0, false"));

  List<String> copyOfForthClue = new ArrayList<String>(forthClue);

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @GetMapping("/api/clues")
  public ResponseEntity<ClueResponse> getClues() {
    return ResponseEntity.ok(
        new ClueResponse(
            "----Escape the Endpoint----",
            "Complete the api call : /api/escape/____ , find the four letter code to escape. The clues for each letter can be obtained by going to /api/clues/{id}",
            List.of("Remember to REST")));
  }

  @GetMapping("/api/clues/{id}")
  public ResponseEntity<ClueResponse> getCluesWithId(@PathVariable("id") int id) throws Exception {

    switch (id) {
      case 1:
        return ResponseEntity.ok(
            new ClueResponse(
                "----Clue for First letter----",
                "The most basic REST function that you have been using already",
                List.of("/api/escape")));
      case 2:
        return ResponseEntity.ok(
            new ClueResponse(
                "---- Clue for Second letter ----",
                "My answer is in the post",
                List.of(
                    "My first is the year I finished my studies, int year",
                    "My Second is my first name, string name",
                    "My last is if I published a game on steam, bool gameIsOut")));
      case 3:
        return ResponseEntity.ok(
            new ClueResponse(
                "---- Clue for Third letter ----",
                "Something needs to be put back in its place -- int index, int value",
                List.of("1", "2", "4", "8", "16", "21", "64", "128", "256")));
      case 4:
        return ResponseEntity.ok(
            new ClueResponse(
                "---- Clue for Forth letter ----",
                "There are too many things here, sometimes binary is the answer  --  int index",
                forthClue));
      default:
        throw new Exception();
    }
  }

  @GetMapping("/api/escape")
  public PuzzleResponseEntity getFirstLetter() {
    return new PuzzleResponseEntity("Okay this was an easy one, just to get you started", 1, 'B');
  }

  @PostMapping("/api/escape")
  public ResponseEntity<PuzzleResponseEntity> createEscape(
      @RequestBody CreateEscapeRequest request) {
    EscapeEntity escape = new EscapeEntity(request.year(), request.name(), request.gameIsOut());

    if (escape.isCorrectValues()) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new PuzzleResponseEntity("Post puzzle, correct!", 2, 'J'));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new PuzzleResponseEntity("Incorrect", 1, '_'));
    }
  }

  @PutMapping("/api/escape")
  public ResponseEntity<PuzzleResponseEntity> changeSequence(
      @RequestParam int index, @RequestParam int value) {
    if (index == 5 && value == 32) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new PuzzleResponseEntity("Put puzzle, correct!", 3, 'Y'));

    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new PuzzleResponseEntity("Incorrect", 3, '_'));
    }
  }

  @DeleteMapping("/api/escape/{index}")
  public HttpStatus deleteSequence(@PathVariable int index) {
    if (index >= 0 && index < copyOfForthClue.size()) {
      copyOfForthClue.remove(index);
      return HttpStatus.OK;
    } else {
      return HttpStatus.BAD_REQUEST;
    }
  }

  @GetMapping("/api/escape/4/reset")
  public HttpStatus resetForthClue() {
    copyOfForthClue = new ArrayList<>(forthClue);
    return HttpStatus.OK;
  }

  @GetMapping("/api/escape/{code}")
  public ResponseEntity<SolutionEntity> solution(@PathVariable String code) {
    String finalCode = "BJYA";
    if (code.equals(finalCode)) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(
              new SolutionEntity(
                  """
					Congratulations!
					you found the correct Endpoint!


					This project was created with the goal for me to learn java and RESTApi

					Thank you for playing
					"""));
    } else {
      String failed = "Incorrect code, failed at : ";
      if (code.length() != 4) {
        failed = "code not 4 letters";
      } else {
        for (int i = 0; i < 4; i++) {
          if (code.charAt(i) != finalCode.charAt(i)) {
            failed += " clue " + (i + 1) + " ; ";
          }
        }
      }
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new SolutionEntity(failed));
    }
  }
}
