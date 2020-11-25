package utils;

public class CyclicCoordinate {
    private int coordinate;
    private int start;
    private int end;

    public CyclicCoordinate(int start, int end, int coordinate) {
        this.start = start;
        this.end = end;
        this.coordinate = coordinate;
    }

    public void setStartAndEnd(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public void plus(int value) {
        System.out.print(coordinate + " + " + value + " = ");
        coordinate = cycle(coordinate + value);
        System.out.println(coordinate);
    }

    public void minus(int value) {
        System.out.print(coordinate + " - " + value + " = ");
        coordinate = cycle(coordinate - value);
        System.out.println(coordinate);
    }

    public int cycle(int value) {
        //System.out.println(value + "%(" + start + ") = " + value%start);
        return mod(value, end - start);
    }

    /*
     * Математически правильное взятие остатка от деления.
     * Определение:
     *      остатком от деление числа a на число b называется
     *             такое число r от 0 до |а|, что
     *                    a = n*b + r,
     *      где n - целое число.
     * То есть 0 <= r < |a|.
     * Примеры:
     *     1) -5 = (-1)*9 + 4 -> остаток -5/9 = 4
     *     2) -191 = (-64)*3 + 1 -> остаток -191/3 = 1
     *     3) 14 = 3*4 + 2 -> остаток 14/4 = 2
     *     4) 100 = 20*5 + 0 -> остаток 100/5 = 0
     * В java оператор % - возвращает отрицательное значение,
     * если число, которое делят меньше 0 (например -5%9 = -5,
     * а не 4 как должно быть или -191%3 = -2, а должно быть 1),
     * а это не корректно, поскольку в математике остаток всегда
     * > или = 0, не зависимо от знака делимого и делителя!
     * Сообственно поэтому я ввел свою операцию mod взятие остатка
     * отделения, которая работает корректно.
     */
    private int mod(int value, int base) {
        return (value%base + base)%base;
    }
}
