package FenwickTree;


/*
    Results are not correct for example
        long[] numbers = {1, 2, 3, 4, 5};

        MyFenwickTree fenwickTree = new MyFenwickTree(numbers);

        System.out.println(fenwickTree.sum(1)); // 2
        System.out.println(fenwickTree.sum(3)); //9
        System.out.println(fenwickTree.sum(1, 3)); //9

    And as i understand, first one should give me 1, and second one should give me 6
 */
public class MyFenwickTree {
    private long[] tree;

    public MyFenwickTree(long[] inputArray) {
        this.tree = inputArray.clone();
        for (int i = 1; i < tree.length; i++) {
            int j = i + lsb(i);
            if (j < tree.length) tree[j] = tree[j] + tree[i];
        }

    }

    private int lsb(int i) {
        return i & -i;
    }


    //but this code ignores the first item in the array
    public long sum(int i) {
        if (i >= tree.length)
            throw new RuntimeException("Range exceeds array length");

        long sum = 0;
        while (i != 0) {
            sum += tree[i];
            i -= lsb(i);
        }
        //I added the below line to contain first element (index 0) value in my result
        // sum += tree[0];
        return sum;
    }

    public long sum(int i, int j) {
        if (j < i) throw new IllegalArgumentException("Make sure j >= i");
        return sum(j) - sum(i - 1);
    }

    public void add(int i, long k) {
        while (i < tree.length) {
            tree[i] += k;
            i += lsb(i);
        }
    }

    public void set(int i, long k) {
        long value = sum(i, i);
        add(i, k - value);
    }


}
