package mi.song.weekand.farm.model;

//재배 작물
public class Crop {
    String name;
    Long startDate;
    String[] imgList;
    String memo;

    public Crop(String name, String[] imgList, String memo){
        this.name = name;
        this.imgList = imgList;
        this.memo = memo;

        startDate = System.currentTimeMillis();
    }
}
