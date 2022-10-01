package me.sansserif.javautils;

public class Args {
    private String[] arguments;
    public Args(String[] args) {
        arguments = args;
    }
    public boolean checkSize(int neededArgumentsCount) {
        return arguments.length == neededArgumentsCount;
    }
    public void checkSizeForced(int neededArgumentsCount) {
        if (arguments.length != neededArgumentsCount) {
            if (arguments.length > neededArgumentsCount) {
                System.out.println("ERROR: You specified more parameters than needed!");
            } else {
                System.out.println("ERROR: You need to specify all parameters!");
            }
            System.exit(1);
        }
    }
    public void checkSizeForced(int neededArgumentsCount, String errorMessage) {
        if (arguments.length != neededArgumentsCount) {
            System.out.println(errorMessage);
            System.exit(1);
        }
    }
    public int isInt(int argNo) throws Exception {
        int parsed = 0;
        try {
            parsed = Integer.parseInt(arguments[argNo]);
        } catch (Exception e) {
            throw new Exception("Argument not an Int!");
        }
        return parsed;
    }
    public long isLong(int argNo) throws Exception {
        long parsed = 0;
        try {
            parsed = Long.parseLong(arguments[argNo]);
        } catch (Exception e) {
            throw new Exception("Argument not a Long!");
        }
        return parsed;
    }
    public float isFloat(int argNo) throws Exception {
        float parsed = 0;
        try {
            parsed = Float.parseFloat(arguments[argNo]);
        } catch (Exception e) {
            throw new Exception("Argument not a Float!");
        }
        return parsed;
    }
    public double isDouble(int argNo) throws Exception {
        double parsed = 0;
        try {
            parsed = Double.parseDouble(arguments[argNo]);
        } catch (Exception e) {
            throw new Exception("Argument not a Double!");
        }
        return parsed;
    }
    public boolean isBoolean(int argNo) throws Exception {
        boolean parsed;
        try {
            parsed = Boolean.parseBoolean(arguments[argNo]);
        } catch (Exception e) {
            throw new Exception("Argument not a Boolean!");
        }
        return parsed;
    }
    public String getString(int argNo) {
        return arguments[argNo];
    }
    public int getInt(int argNo) throws Exception {
        return isInt(argNo);
    }
    public long getLong(int argNo) throws Exception {
        return isLong(argNo);
    }
    public float getFloat(int argNo) throws Exception {
        return isFloat(argNo);
    }
    public double getDouble(int argNo) throws Exception {
        return isDouble(argNo);
    }
    public boolean getBoolean(int argNo) throws Exception {
        return isBoolean(argNo);
    }
    public void mandatoryArg(int argNo, String equal, String errormsg) {
        if (!arguments[argNo].equals(equal)) {
            System.out.println(errormsg);
            System.exit(1);
        }
    }
    public void mandatoryArg(int argNo, String equal) {
        if (!arguments[argNo].equals(equal)) {
            System.out.println("ERROR: False argument! (arg No. " + argNo + ")");
            System.exit(1);
        }
    }
}
