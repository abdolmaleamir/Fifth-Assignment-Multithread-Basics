package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        List<List<Integer>> tempMatrixProduct;
        int num;
        List<List<Integer>> matrix_A, matrix_B;
        public BlockMultiplier(int num, List<List<Integer>> matrix_A, List<List<Integer>> matrix_B) {
            this.num = num;
            this.matrix_A = matrix_A;
            this.matrix_B = matrix_B;
            tempMatrixProduct = new ArrayList<>();
        }

        @Override
        public void run() {
            int x = 0, y = 0;
            int p, q, r;
            p = matrix_A.size();
            q = matrix_B.size();
            r = matrix_B.get(0).size();
            if (num == 2 || num == 4)
                y = r / 2;
            if (num == 3 || num == 4)
                x = p / 2;
            for (int i = 0; i < p / 2; i++) {
                List<Integer> satr = new ArrayList<>();
                for (int j = 0; j < r / 2; j++) {
                    int ans = 0;
                    for (int k = 0; k < q; k++) {
                        ans += matrix_A.get(x + i).get(k) * matrix_B.get(k).get(y + j);
                    }
                    satr.add(ans);
                }
                tempMatrixProduct.add(satr);
            }
        }
        public List<List<Integer>> getTempMatrixProduct() {
            return tempMatrixProduct;
        }
    }

    /*
    Matrix A is of the form p x q
    Matrix B is of the form q x r
    both p and r are even numbers
    */
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B)
    {
        List<List<Integer>> ParallelizeMatMul = new ArrayList<>();

        List<List<Integer>> mat1 = new ArrayList<>();
        List<List<Integer>> mat2 = new ArrayList<>();
        List<List<Integer>> mat3 = new ArrayList<>();
        List<List<Integer>> mat4 = new ArrayList<>();

        BlockMultiplier BM1 = new BlockMultiplier(1, matrix_A, matrix_B);
        BlockMultiplier BM2 = new BlockMultiplier(2, matrix_A, matrix_B);
        BlockMultiplier BM3 = new BlockMultiplier(3, matrix_A, matrix_B);
        BlockMultiplier BM4 = new BlockMultiplier(4, matrix_A, matrix_B);

        Thread thread1 = new Thread(BM1);
        Thread thread2 = new Thread(BM2);
        Thread thread3 = new Thread(BM3);
        Thread thread4 = new Thread(BM4);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            mat1 = BM1.getTempMatrixProduct();
            thread2.join();
            mat2 = BM2.getTempMatrixProduct();
            thread3.join();
            mat3 = BM3.getTempMatrixProduct();
            thread4.join();
            mat4 = BM4.getTempMatrixProduct();
        }catch (InterruptedException e) {
            System.out.println("thread Interrupted.");
        }
        for (int i = 0; i < mat1.size(); i++) {
            List<Integer> satr = new ArrayList<>();
            for (int j = 0; j < mat1.get(0).size(); j++)
                satr.add(mat1.get(i).get(j));
            for (int j = 0; j < mat2.get(0).size(); j++)
                satr.add(mat2.get(i).get(j));
            ParallelizeMatMul.add(satr);
        }
        for (int i = 0; i < mat3.size(); i++) {
            List<Integer> satr = new ArrayList<>();
            for (int j = 0; j < mat3.get(0).size(); j++)
                satr.add(mat3.get(i).get(j));
            for (int j = 0; j < mat4.get(0).size(); j++)
                satr.add(mat4.get(i).get(j));
            ParallelizeMatMul.add(satr);
        }
        return ParallelizeMatMul;
    }

    public static void main(String[] args) {

    }
}