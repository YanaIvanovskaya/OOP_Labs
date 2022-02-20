////package Lab1_Task1;
//
////class Replacer {
////
////    static class Arguments {
////
////        public String inputPath;
////        public String outputPath;
////        public String searchStr;
////        public String replaceStr;
////
////        Arguments(String inputPath, String outputPath, String searchStr, String replaceStr) {
////            this.inputPath = inputPath;
////            this.outputPath = outputPath;
////            this.searchStr = searchStr;
////            this.replaceStr = replaceStr;
////        }
////    }
////
////    static String searchStr = "Long text for search";
////    static String replaceStr = "REPLACED";
////
////    public static void main(String[] args) {
////        Arguments arguments = getArgumentsOrNull(args);
////
////        String inputPath = "C:\\Users\\yana-\\Desktop\\input.txt";
////
////        String outputPath = "C:\\Users\\yana-\\Desktop\\output.txt";
////
////        FileWriter writer = createFileWriter(new File(outputPath));
////
////        if (writer == null) {
////            return;
////        }
////
////        readInputFile(new File(inputPath), writer);
//
//        //readInputFile(new File(arguments.inputPath));
//
////        if (arguments == null) {
////            System.out.println("Incorrect arguments");
////            return;
////        }
////        if (!(isValidFilePath(arguments.inputPath) && isValidFilePath(arguments.outputPath))) {
////            System.out.println("Invalid file paths");
////            return;
////        }
//
//
//    }
//
////
////    static void test() {
////        String[] listOfLines = "".split("\n");
////
////        StringBuilder lineBuffer = new StringBuilder();
////        String replacedLine;
////        boolean isNewLineNotContainSearchText;
////        boolean isReplacedLineNotContainSearchText;
////
////        for (String newLine : listOfLines) {
////            replacedLine = newLine.replaceAll(searchStr, replaceStr);
////            isNewLineNotContainSearchText = isNotContainAnyPartOf(newLine, searchStr);
////            isReplacedLineNotContainSearchText = isNotContainAnyPartOf(replacedLine, searchStr);
////
////            if (isNewLineNotContainSearchText || isReplacedLineNotContainSearchText) {
////                writeIfNotEmpty(lineBuffer.toString());
////                lineBuffer.setLength(0);
////
////                if (isNewLineNotContainSearchText) {
////                    writeIfNotEmpty(newLine);
////                } else {
////                    writeIfNotEmpty(replacedLine);
////                }
////                continue;
////            }
////
////            replacedLine = lineBuffer
////                    .append(" ")
////                    .append(newLine)
////                    .toString()
////                    .replaceAll(searchStr, replaceStr)
////                    .trim();
////            lineBuffer.setLength(0);
////            lineBuffer.append(replacedLine);
////        }
////    }
//
//
////    static String prepareLineForWriting(String line) {
////        StringBuilder lineBuffer = new StringBuilder();
////        String replacedLine;
////        String preparedLine = "";
////
////        replacedLine = line.replaceAll(searchStr, replaceStr);
////        boolean isNewLineNotContainSearchText = isNotContainAnyPartOf(line, searchStr);
////        boolean isReplacedLineNotContainSearchText = isNotContainAnyPartOf(replacedLine, searchStr);
////
////        if (isNewLineNotContainSearchText || isReplacedLineNotContainSearchText) {
////            preparedLine = lineBuffer.toString();
////            lineBuffer.setLength(0);
////
////            if (isNewLineNotContainSearchText) {
////                preparedLine += line + "\n";
////            } else {
////                preparedLine += replacedLine + "\n";
////            }
////        }
////        replacedLine = lineBuffer
////                .append(" ")
////                .append(line)
////                .toString()
////                .replaceAll(searchStr, replaceStr)
////                .trim();
////        lineBuffer.setLength(0);
////        lineBuffer.append(replacedLine);
////        System.out.println("lineBuffer " + lineBuffer);
////        preparedLine += lineBuffer + "\n";
////        return preparedLine;
////    }
//
//
////
////    static void writeIfNotEmpty(String line) {
////        if (line.isEmpty()) {
////            return;
////        }
////        System.out.println(line.trim());
////    }
//
////    static int percentOfString(String text, String search) {
////        String[] searchStringList = search.split(" ");
////        int searchStringListLength = searchStringList.length;
////
////        int counter = 0;
////
////        for (int i = 0; i < searchStringListLength; i++) {
////            String partOfSearch = Arrays.stream(
////                    Arrays.copyOfRange(searchStringList, 0, i + 1))
////                    .reduce((String acc, String str2) -> acc += " " + str2)
////                    .orElse("");
////            System.out.println(partOfSearch);
////            if (text.contains(partOfSearch)) {
////                counter++;
////            }
////        }
////        return (int) (((double) counter / searchStringListLength) * 100);
////    }
//
//
////}
//
//
//
