package main.java;

public class PCB {
    int iD;
    State currentStatus;
    int PC;
    int start;
    int end;

    public PCB(int iD, State currentStatus, int PC, int start, int end) {
        this.iD = iD;
        this.currentStatus = currentStatus;
        this.PC = PC;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "iD=" + iD +
                ", currentStatus=" + currentStatus +
                ", PC=" + PC +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
