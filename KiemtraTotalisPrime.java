package Btvn;

import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;

public class KiemtraTotalisPrime implements Runnable{
        private BlockingQueue<Model_tuoi> outputQueue;
        
        public KiemtraTotalisPrime() {
        	
        }

        public KiemtraTotalisPrime(BlockingQueue<Model_tuoi> outputQueue) {
            this.outputQueue=outputQueue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                	Model_tuoi tuoi= outputQueue.take();                    
                    if (tuoi == null) {
                        break;
                    }
                    checkPrimeNumber(tuoi.getAge(),tuoi.getDay(),tuoi.getMonth()); 
                }
            } catch (InterruptedException e) {
                
            }
        }
	private static Boolean chek;
	public static synchronized void checkPrimeNumber(int a,int b,int c) {
        	int total=a+b+c;
        	Boolean check=isPrime(total);
        	if(check) {
        		System.out.println("Tổng ngày"+c+",tháng"+b+",năm"+a+" là số nguyên tố");
        	}
        	else {
        		System.out.println("Tổng ngày"+c+",tháng"+b+",năm"+a+" không phải là số nguyên tố");
        	}
        }
	 synchronized static boolean isPrime(int number) {
	    if (number < 2) {
	        return false;
	    }
	    for (int i = 2; i <= Math.sqrt(number); i++) {
	        if (number % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}

	private synchronized Boolean isDigit() {
		return chek;
	}
}
