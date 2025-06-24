package com.back;

import java.util.Scanner;

public class App {
    private Scanner scanner = new Scanner(System.in);
    private int lastId = 0;
    private WiseSaying[] wiseSayings = new WiseSaying[100];
    private int wiseSayingslastIndex = -1;

    public void run() {
        System.out.println("==명언 앱 == 가즈아 ==");

        while (true) {
            System.out.printf("명령) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("종료")) {
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
        System.out.println("---------------------");

        WiseSaying[] forListWiseSayings = findForList();

        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.printf("%d / %s / %s\n", wiseSaying.getId(), wiseSaying.getContent(), wiseSaying.getAuthor());
        }
    }

    private void actionDelete(String cmd) {
        String[] cmdBits = cmd.split("=", 2);

        if (cmdBits.length < 2 || cmdBits[1].trim().isEmpty()) {
            System.out.println("삭제할 명언의 id를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[1].trim());

        int deletedIndex = delete(id);

        if (deletedIndex == -1) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    private void actionModify(String cmd) {
        String[] cmdBits = cmd.split("=", 2);

        if (cmdBits.length < 2 || cmdBits[1].trim().isEmpty()) {
            System.out.println("수정할 명언의 id를 입력해주세요.");
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

    private int getsize() {
        return wiseSayingslastIndex + 1;
    }

    private int findIndexById(int id) {
        for (int i = 0; i <= wiseSayingslastIndex; i++) {
            if (wiseSayings[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private WiseSaying findById(int id) {
        int index = findIndexById(id);

        if (index == -1) return null;

        return wiseSayings[index];
    }

    private WiseSaying[] findForList() {
        WiseSaying[] forListWiseSayings = new WiseSaying[getsize()];

        int forListWiseSayingsIndex = -1;

        for (int i = wiseSayingslastIndex; i >= 0; i--) {
            forListWiseSayings[++forListWiseSayingsIndex] = wiseSayings[i];
        }

        return forListWiseSayings;
    }

    private WiseSaying write(String content, String author) {

        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);
        wiseSayings[++wiseSayingslastIndex] = wiseSaying;
        return wiseSaying;
    }

    private int delete(int id) {
        int deleteIndex = findIndexById(id);

        if (deleteIndex == -1) return deleteIndex;

        for (int i = deleteIndex + 1; i <= wiseSayingslastIndex; i++) {
            wiseSayings[i - 1] = wiseSayings[i];
        }

        wiseSayings[wiseSayingslastIndex] = null;
        wiseSayingslastIndex--;

        return deleteIndex;
    }

    private void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
    }
}
