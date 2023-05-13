package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        List<String> parts = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(signatureString, "()");
        while (tokenizer.hasMoreElements()) {
            parts.add(tokenizer.nextToken());
        }
        String[] name = parts.get(0).split(" ");

        MethodSignature result;

        if (parts.size() > 1) {
            String[] arguments = parts.get(1).split(", ");
            List<MethodSignature.Argument> args = new ArrayList<>();

            for (int i = 0; i < arguments.length; i++) {
                String[] arg = arguments[i].split(" ");
                MethodSignature.Argument argument = new MethodSignature.Argument(arg[0], arg[1]);
                args.add(argument);
            }

            result = new MethodSignature(name[name.length - 1], args);
        } else {
            result = new MethodSignature(name[name.length - 1]);
        }

        switch (name[0]) {
            case "private":
                result.setAccessModifier("private");
                break;
            case "protected":
                result.setAccessModifier("protected");
                break;
            case "public":
                result.setAccessModifier("public");
                break;
            default:
                result.setAccessModifier(null);
                break;
        }

        if (name.length == 3) {
            result.setReturnType(name[1]);
        } else if (name.length <= 2) {
            result.setReturnType(name[0]);
        }
        return result;
    }
}
