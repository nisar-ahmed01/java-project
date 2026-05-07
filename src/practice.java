//class IdUtils{
//    public static boolean isValidId(String id){
//        if(!(id == null) && id.length()>=6 && Character.isDigit(id.charAt(0)))
//            return true;
//        else
//            return false;
//    }
//    public static String maskId(String id){
//        if(id == null || id.length()<4)
//            return "*";
//        else
//            return id.substring(0,2) + "**" +id.substring(id.length()-2,id.length());
//    }
//}
//class Book{
//        private String title;
//        private boolean onShelf;
//        Book(String title){
//            this.title = title;
//            this.onShelf = true;
//        }
//        public boolean borrow(){
//            if(onShelf){
//                onShelf = false;
//                return true;
//            }
//            else
//                return false;
//        }
//        public void bringBack(){
//            onShelf = true;
//        }
//}
//class Student{
//    private String name,studentId;
//    private Book borrowed;
//    Student(String name,String studentId){
//        this.name = name;
//        this.studentId = studentId;
//    }
//    public boolean takeBook(Book b) {
//        if (b == null || borrowed != null || b.borrow() == false) {
//            return false;
//        }
//        else {
//            borrowed = b;
//            return true;
//        }
//    }
//    public boolean returnBook(){
//        if(borrowed == null)
//            return false;
//        else {
//            borrowed.bringBack();
//            borrowed = null;
//            return true;
//        }
//    }
//}
//public class practice{
//public static void main(String[] args) {
//    System.out.println(IdUtils.isValidId("12ABCD99"));
//    System.out.println(IdUtils.maskId("12ABCD99"));
//    Book book1 = new Book("C++");
//    Book book2 = new Book("Java");
//    Student student1 = new Student("Nisar","STU001");
//    System.out.println(student1.takeBook(book1));
//    System.out.println(student1.takeBook(book2));
//    System.out.println(student1.returnBook());
//    System.out.println(student1.takeBook(book2));
//    }
//}


class VehicleUtils{
    public static boolean isValidPlate(String plate){
        return (plate != null && plate.length()>=5 && plate.length()<=8) && Character.isLetter(plate.charAt(0));
    }
    public static String maskPlate(String plate){
        if(plate == null || plate.length()<3){
            return "XXX";
        }
        return plate.charAt(0) + "***" + plate.charAt(plate.length()-1);
    }
}
class ParkingSpot {
    private int spotNumber;
    private boolean isAvailable;
    ParkingSpot(int spotNumber){
        this.spotNumber = spotNumber;
        this.isAvailable = true;
    }
    public boolean occupy() {
        if (isAvailable) {
            isAvailable = false;
            return true;
        }
        return false;
    }
    public void vacate(){
        isAvailable = true;
    }
}
class Vehicle {
    private String ownerName,licensePlate;
    private ParkingSpot assignedSpot;
    Vehicle(String ownerName, String licensePlate){
        this.ownerName = ownerName;
        this.licensePlate = licensePlate;
    }
    public boolean park(ParkingSpot spot){
        if(spot == null || assignedSpot != null || spot.occupy() == false)
            return false;
        assignedSpot = spot;
        return true;
    }
    public boolean leaveParking(){
        if(assignedSpot == null)
            return false;
        assignedSpot.vacate();
        assignedSpot = null;
        return true;
    }
}
public class practice{
public static void main(String[] args) {
    System.out.println(VehicleUtils.isValidPlate("ABC1234"));
    System.out.println(VehicleUtils.maskPlate("ABC1234"));
    ParkingSpot spot1 = new ParkingSpot(1);
    ParkingSpot spot2 = new ParkingSpot(2);
    Vehicle vehicle1 = new Vehicle("Nisar","ABC-123");
    System.out.println(vehicle1.park(spot1));
    System.out.println(vehicle1.park(spot2));
    System.out.println(vehicle1.leaveParking());
    System.out.println(vehicle1.park(spot2));
}
}

