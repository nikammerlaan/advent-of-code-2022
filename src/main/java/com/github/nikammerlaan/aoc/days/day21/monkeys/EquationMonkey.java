package com.github.nikammerlaan.aoc.days.day21.monkeys;

public class EquationMonkey extends Monkey {

    private Monkey a;
    private Monkey b;

    public EquationMonkey(Monkey a, Monkey b) {
        super(true);

        this.a = a;
        this.b = b;
    }

    @Override
    public long getValue() {
        // Swap so a always has the monkey with the variable in it
        if(b.hasVariable()) {
            var temp = a;
            a = b;
            b = temp;
        }

        while(true) {
            if(a instanceof VariableMonkey) {
                return b.getValue();
            } else if(a instanceof FunctionMonkey f) {
                var c = f.getMonkeyA();
                var d = f.getMonkeyB();

                if(c.hasVariable()) {
                    b = switch(f) {
                        case AdditionMonkey       __ -> new SubtractionMonkey(b, d);
                        case SubtractionMonkey    __ -> new AdditionMonkey(b, d);
                        case MultiplicationMonkey __ -> new DivisionMonkey(b, d);
                        case DivisionMonkey       __ -> new MultiplicationMonkey(b, d);
                    };
                    a = c;
                } else {
                    b = switch(f) {
                        case AdditionMonkey       __ -> new SubtractionMonkey(b, c);
                        case SubtractionMonkey    __ -> new SubtractionMonkey(c, b);
                        case MultiplicationMonkey __ -> new DivisionMonkey(b, c);
                        case DivisionMonkey       __ -> new DivisionMonkey(c, b);
                    };
                    a = d;
                }
            }
        }
    }

}
