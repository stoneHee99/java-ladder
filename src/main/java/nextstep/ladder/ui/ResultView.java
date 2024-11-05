package nextstep.ladder.ui;

import nextstep.ladder.domain.GameResult;
import nextstep.ladder.domain.Ladder;
import nextstep.ladder.domain.Line;


import java.util.List;
import java.util.stream.Collectors;

public class ResultView {
    private static final int FORMAT_LENGTH = 5;
    private static final String PRINT_ALL_RESULT_COMMAND = "all";

    public static void printLadder(Ladder ladder) {
        printResultMessage();
        System.out.println(generateLadderString(ladder));
    }

    private static void printResultMessage() {
        printBlankLine();
        System.out.println("사다리 결과");
        printBlankLine();
    }

    private static void printBlankLine() {
        System.out.println();
    }

    private static String generateLadderString(Ladder ladder) {
        StringBuilder result = new StringBuilder();
        appendParticipantNames(result, ladder.participantNames());
        appendLadderRows(result, ladder.lines(), ladder.participantCount());
        appendResultRow(result, ladder.getResults());
        return result.toString();
    }

    private static void appendParticipantNames(StringBuilder result, List<String> names) {
        String namesRow = names.stream()
                .map(ResultView::formatForView)
                .collect(Collectors.joining(" "));
        result.append(namesRow).append("\n");
    }

    private static String formatForView(String text) {
        int totalWidth = FORMAT_LENGTH;
        int textLength = text.length();
        int leftPadding = (totalWidth - textLength) / 2;
        int rightPadding = totalWidth - textLength - leftPadding;

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    private static void appendLadderRows(StringBuilder result, List<Line> lines, int participantCount) {
        lines.forEach(line ->
                result.append(generateLadderRow(line, participantCount)).append("\n")
        );
    }

    private static String generateLadderRow(Line line, int participantCount) {
        StringBuilder builder = new StringBuilder();
        builder.append("    ");

        for (int i = 0; i < participantCount - 1; i++) {
            builder.append("|");
            builder.append(line.points().isConnected(i) ? "-----" : "     ");
        }
        builder.append("|");

        return builder.toString();
    }

    private static void appendResultRow(StringBuilder result, List<String> results) {
        String resultRow = results.stream()
                .map(ResultView::formatForView)
                .collect(Collectors.joining(" "));
        result.append(resultRow).append("\n");
    }

    public static void printResult(String name, Ladder ladder) {
        printBlankLine();
        if (PRINT_ALL_RESULT_COMMAND.equalsIgnoreCase(name)) {
            printAllResults(ladder.findAllResults());
            return;
        }
        System.out.println("실행 결과");
        System.out.println(ladder.findResult(name));
        printBlankLine();
    }

    private static void printAllResults(GameResult gameResult) {
        System.out.println("실행 결과");
        gameResult.getAllResults()
                .forEach((name, result) ->
                System.out.println(name + " : " + result)
        );
    }
}
