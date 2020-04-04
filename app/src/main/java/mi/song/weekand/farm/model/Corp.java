package mi.song.weekand.farm.model;

//재배 작물
public class Corp {
    String name;
    Long startDate;
    String[] imgList;
    String memo;

    public Corp(String name, String[] imgList, String memo){
        this.name = name;
        this.imgList = imgList;
        this.memo = memo;

        startDate = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public Long getStartDate() {
        return startDate;
    }

    public String[] getImgList() {
        return imgList;
    }

    public String getMemo() {
        return memo;
    }
}
