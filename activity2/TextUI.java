public class TextUI {
    public static void main(String[] args) {
        new Thread(() -> {
            double tempC = 20.0;
            while (true){
                tempC += (Math.random() * 2 - 1);
                double tempK = tempC + 273.15;
                System.out.println("Reading is " + String.format("%.2f", tempC) 
                + " degrees C and " + String.format("%.2f", tempK) + " degrees K");
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }).start();
    }
}
