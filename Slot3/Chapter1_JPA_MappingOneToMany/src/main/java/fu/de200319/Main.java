package fu.de200319;



import fu.de200319.util.JPAUtil;

public class Main {
    public static void main(String[] args) {
        // Gọi lệnh này để khởi tạo EntityManagerFactory
        JPAUtil.getEMF();
        System.out.println("Ket noi va tao bang thanh cong!");
        JPAUtil.close();
    }
}