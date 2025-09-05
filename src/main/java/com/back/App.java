package com.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    int lastId = 0;
    List<WiseSaying> wiseSayings = new ArrayList<>();

    void run() {
        System.out.println("명언시작");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (cmd.equals("등록")) {
                actionWrite();
            } else if (cmd.equals("목록")) {
                actionList();
            } else if (cmd.startsWith("삭제")) {
                actionDelete(cmd);
            } else if (cmd.startsWith("수정")) {
                actionModify(cmd);
            }
        }
        scanner.close();
    }

    private void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        WiseSaying wiseSaying = write(content, author);

        System.out.printf("%d번 명언이 등록되었습니다.\n", wiseSaying.getId());
    }

    private void actionList() {
        System.out.println("번호 / 명언 / 작가");
        System.out.println("-------------------------");

        List<WiseSaying> forListWiseSayings = findForList();

        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.printf("%d / %s / %s\n",
                    wiseSaying.getId(),
                    wiseSaying.getContent(),
                    wiseSaying.getAuthor());
        }
    }

    private void actionDelete(String cmd) {
        String[] cmdBits = cmd.split("=",2);

        if (cmdBits.length < 2 || cmdBits[1].trim().isEmpty()) {
            System.out.println("삭제할 명언의 번호를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[1].trim());

        int deleteIndex = delete(id);

        if (deleteIndex == -1) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    private void actionModify(String cmd) {
        String[] cmdBits = cmd.split("=", 2);

        if (cmdBits.length < 2 || cmdBits[1].trim().isEmpty()) {
            System.out.println("수정할 명언의 번호를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[1].trim());

        WiseSaying wiseSaying = findById(id);

        if (wiseSaying == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        System.out.printf("명언(기존) : %s\n", wiseSaying.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.printf("작가(기존) : %s\n", wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        modify(wiseSaying, content, author);

        System.out.printf("%d번 명언이 수정되었습니다.\n", id);
    }

    WiseSaying findById(int id) {
        return wiseSayings
                .stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .orElse(null);
    }

    List<WiseSaying> findForList() {
        return wiseSayings.reversed();
    }

    private WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);

        wiseSayings.add(wiseSaying);

        return wiseSaying;
    }

    private void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
    }

    private int delete(int id) {
        WiseSaying wiseSaying = findById(id);

        if (wiseSaying == null) return -1;

        wiseSayings.remove(wiseSaying);

        return wiseSaying.getId();
    }
}