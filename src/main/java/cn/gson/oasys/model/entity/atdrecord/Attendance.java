package cn.gson.oasys.model.entity.atdrecord;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aoa_attendance")
public class Attendance {

    @Id
    @Column(name="id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name="dept")
    private String dept;
    @Column(name="day1am")
    private String day1am;
    @Column(name="day1pm")
    private String day1pm;
    @Column(name="day2am")
    private String day2am;
    @Column(name="day2pm")
    private String day2pm;
    @Column(name="day3am")
    private String day3am;
    @Column(name="day3pm")
    private String day3pm;
    @Column(name="day4am")
    private String day4am;
    @Column(name="day4pm")
    private String day4pm;
    @Column(name="day5am")
    private String day5am;
    @Column(name="day5pm")
    private String day5pm;
    @Column(name="day6am")
    private String day6am;
    @Column(name="day6pm")
    private String day6pm;
    @Column(name="day7am")
    private String day7am;
    @Column(name="day7pm")
    private String day7pm;
    @Column(name="day8am")
    private String day8am;
    @Column(name="day8pm")
    private String day8pm;
    @Column(name="day9am")
    private String day9am;
    @Column(name="day9pm")
    private String day9pm;
    @Column(name="day10am")
    private String day10am;
    @Column(name="day10pm")
    private String day10pm;
    @Column(name="day11am")
    private String day11am;
    @Column(name="day11pm")
    private String day11pm;
    @Column(name="day12am")
    private String day12am;
    @Column(name="day12pm")
    private String day12pm;
    @Column(name="day13am")
    private String day13am;
    @Column(name="day13pm")
    private String day13pm;
    @Column(name="day14am")
    private String day14am;
    @Column(name="day14pm")
    private String day14pm;
    @Column(name="day15am")
    private String day15am;
    @Column(name="day15pm")
    private String day15pm;
    @Column(name="day16am")
    private String day16am;
    @Column(name="day16pm")
    private String day16pm;
    @Column(name="day17am")
    private String day17am;
    @Column(name="day17pm")
    private String day17pm;
    @Column(name="day18am")
    private String day18am;
    @Column(name="day18pm")
    private String day18pm;
    @Column(name="day19am")
    private String day19am;
    @Column(name="day19pm")
    private String day19pm;
    @Column(name="day20am")
    private String day20am;
    @Column(name="day20pm")
    private String day20pm;
    @Column(name="day21am")
    private String day21am;
    @Column(name="day21pm")
    private String day21pm;
    @Column(name="day22am")
    private String day22am;
    @Column(name="day22pm")
    private String day22pm;
    @Column(name="day23am")
    private String day23am;
    @Column(name="day23pm")
    private String day23pm;
    @Column(name="day24am")
    private String day24am;
    @Column(name="day24pm")
    private String day24pm;
    @Column(name="day25am")
    private String day25am;
    @Column(name="day25pm")
    private String day25pm;
    @Column(name="day26am")
    private String day26am;
    @Column(name="day26pm")
    private String day26pm;
    @Column(name="day27am")
    private String day27am;
    @Column(name="day27pm")
    private String day27pm;
    @Column(name="day28am")
    private String day28am;
    @Column(name="day28pm")
    private String day28pm;
    @Column(name="day29am")
    private String day29am;
    @Column(name="day29pm")
    private String day29pm;
    @Column(name="day30am")
    private String day30am;
    @Column(name="day30pm")
    private String day30pm;
    @Column(name="day31am")
    private String day31am;
    @Column(name="day31pm")
    private String day31pm;
    @Column(name="latetimes")
    private Integer latetimes;
    @Column(name="latetime")
    private Integer latetime;
    @Column(name="aplogy")
    private Integer aplogy;
    @Column(name="absenteeism")
    private Double absenteeism;
    @Column(name="trouble")
    private Double trouble;
    @Column(name="sick")
    private Double sick;
    @Column(name="year")
    private Double year;
    @Column(name="special")
    private Double special;
    @Column(name="begindate")
    private String begindate;
    @Column(name="enddate")
    private String enddate;
    @Column(name="morning")
    private String morning;


    @Column(name="evening")
    private String evening;

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getEvening() {
        return evening;
    }

    public void setEvening(String evening) {
        this.evening = evening;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDay1am() {
        return day1am;
    }

    public void setDay1am(String day1am) {
        this.day1am = day1am;
    }

    public String getDay1pm() {
        return day1pm;
    }

    public void setDay1pm(String day1pm) {
        this.day1pm = day1pm;
    }

    public String getDay2am() {
        return day2am;
    }

    public void setDay2am(String da2am) {
        this.day2am = day2am;
    }

    public String getDay2pm() {
        return day2pm;
    }

    public void setDay2pm(String day2pm) {
        this.day2pm = day2pm;
    }

    public String getDay3am() {
        return day3am;
    }

    public void setDay3am(String day3am) {
        this.day3am = day3am;
    }

    public String getDay3pm() {
        return day3pm;
    }

    public void setDay3pm(String day3pm) {
        this.day3pm = day3pm;
    }

    public String getDay4am() {
        return day4am;
    }

    public void setDay4am(String day4am) {
        this.day4am = day4am;
    }

    public String getDay4pm() {
        return day4pm;
    }

    public void setDay4pm(String day4pm) {
        this.day4pm = day4pm;
    }

    public String getDay5am() {
        return day5am;
    }

    public void setDay5am(String day5am) {
        this.day5am = day5am;
    }

    public String getDay5pm() {
        return day5pm;
    }

    public void setDay5pm(String day5pm) {
        this.day5pm = day5pm;
    }

    public String getDay6am() {
        return day6am;
    }

    public void setDay6am(String day6am) {
        this.day6am = day6am;
    }

    public String getDay6pm() {
        return day6pm;
    }

    public void setDay6pm(String day6pm) {
        this.day6pm = day6pm;
    }

    public String getDay7am() {
        return day7am;
    }

    public void setDay7am(String day7am) {
        this.day7am = day7am;
    }

    public String getDay7pm() {
        return day7pm;
    }

    public void setDay7pm(String day7pm) {
        this.day7pm = day7pm;
    }

    public String getDay8am() {
        return day8am;
    }

    public void setDay8am(String day8am) {
        this.day8am = day8am;
    }

    public String getDay8pm() {
        return day8pm;
    }

    public void setDay8pm(String day8pm) {
        this.day8pm = day8pm;
    }

    public String getDay9am() {
        return day9am;
    }

    public void setDay9am(String day9am) {
        this.day9am = day9am;
    }

    public String getDay9pm() {
        return day9pm;
    }

    public void setDay9pm(String day9pm) {
        this.day9pm = day9pm;
    }

    public String getDay10am() {
        return day10am;
    }

    public void setDay10am(String day10am) {
        this.day10am = day10am;
    }

    public String getDay10pm() {
        return day10pm;
    }

    public void setDay10pm(String day10pm) {
        this.day10pm = day10pm;
    }

    public String getDay11am() {
        return day11am;
    }

    public void setDay11am(String day11am) {
        this.day11am = day11am;
    }

    public String getDay11pm() {
        return day11pm;
    }

    public void setDay11pm(String day11pm) {
        this.day11pm = day11pm;
    }

    public String getDay12am() {
        return day12am;
    }

    public void setDay12am(String day12am) {
        this.day12am = day12am;
    }

    public String getDay12pm() {
        return day12pm;
    }

    public void setDay12pm(String day12pm) {
        this.day12pm = day12pm;
    }

    public String getDay13am() {
        return day13am;
    }

    public void setDay13am(String day13am) {
        this.day13am = day13am;
    }

    public String getDay13pm() {
        return day13pm;
    }

    public void setDay13pm(String day13pm) {
        this.day13pm = day13pm;
    }

    public String getDay14am() {
        return day14am;
    }

    public void setDay14am(String day14am) {
        this.day14am = day14am;
    }

    public String getDay14pm() {
        return day14pm;
    }

    public void setDay14pm(String day14pm) {
        this.day14pm = day14pm;
    }

    public String getDay15am() {
        return day15am;
    }

    public void setDay15am(String day15am) {
        this.day15am = day15am;
    }

    public String getDay15pm() {
        return day15pm;
    }

    public void setDay15pm(String day15pm) {
        this.day15pm = day15pm;
    }

    public String getDay16am() {
        return day16am;
    }

    public void setDay16am(String day16am) {
        this.day16am = day16am;
    }

    public String getDay16pm() {
        return day16pm;
    }

    public void setDay16pm(String day16pm) {
        this.day16pm = day16pm;
    }

    public String getDay17am() {
        return day17am;
    }

    public void setDay17am(String day17am) {
        this.day17am = day17am;
    }

    public String getDay17pm() {
        return day17pm;
    }

    public void setDay17pm(String day17pm) {
        this.day17pm = day17pm;
    }

    public String getDay18am() {
        return day18am;
    }

    public void setDay18am(String day18am) {
        this.day18am = day18am;
    }

    public String getDay18pm() {
        return day18pm;
    }

    public void setDay18pm(String day18pm) {
        this.day18pm = day18pm;
    }

    public String getDay19am() {
        return day19am;
    }

    public void setDay19am(String day19am) {
        this.day19am = day19am;
    }

    public String getDay19pm() {
        return day19pm;
    }

    public void setDay19pm(String day19pm) {
        this.day19pm = day19pm;
    }

    public String getDay20am() {
        return day20am;
    }

    public void setDay20am(String day20am) {
        this.day20am = day20am;
    }

    public String getDay20pm() {
        return day20pm;
    }

    public void setDay20pm(String day20pm) {
        this.day20pm = day20pm;
    }

    public String getDay21am() {
        return day21am;
    }

    public void setDay21am(String day21am) {
        this.day21am = day21am;
    }

    public String getDay21pm() {
        return day21pm;
    }

    public void setDay21pm(String day21pm) {
        this.day21pm = day21pm;
    }

    public String getDay22am() {
        return day22am;
    }

    public void setDay22am(String day22am) {
        this.day22am = day22am;
    }

    public String getDay22pm() {
        return day22pm;
    }

    public void setDay22pm(String day22pm) {
        this.day22pm = day22pm;
    }

    public String getDay23am() {
        return day23am;
    }

    public void setDay23am(String day23am) {
        this.day23am = day23am;
    }

    public String getDay23pm() {
        return day23pm;
    }

    public void setDay23pm(String day23pm) {
        this.day23pm = day23pm;
    }

    public String getDay24am() {
        return day24am;
    }

    public void setDay24am(String day24am) {
        this.day24am = day24am;
    }

    public String getDay24pm() {
        return day24pm;
    }

    public void setDay24pm(String day24pm) {
        this.day24pm = day24pm;
    }

    public String getDay25am() {
        return day25am;
    }

    public void setDay25am(String day25am) {
        this.day25am = day25am;
    }

    public String getDay25pm() {
        return day25pm;
    }

    public void setDay25pm(String day25pm) {
        this.day25pm = day25pm;
    }

    public String getDay26am() {
        return day26am;
    }

    public void setDay26am(String day26am) {
        this.day26am = day26am;
    }

    public String getDay26pm() {
        return day26pm;
    }

    public void setDay26pm(String day26pm) {
        this.day26pm = day26pm;
    }

    public String getDay27am() {
        return day27am;
    }

    public void setDay27am(String day27am) {
        this.day27am = day27am;
    }

    public String getDay27pm() {
        return day27pm;
    }

    public void setDay27pm(String day27pm) {
        this.day27pm = day27pm;
    }

    public String getDay28am() {
        return day28am;
    }

    public void setDay28am(String day28am) {
        this.day28am = day28am;
    }

    public String getDay28pm() {
        return day28pm;
    }

    public void setDay28pm(String day28pm) {
        this.day28pm = day28pm;
    }

    public String getDay29am() {
        return day29am;
    }

    public void setDay29am(String day29am) {
        this.day29am = day29am;
    }

    public String getDay29pm() {
        return day29pm;
    }

    public void setDay29pm(String day29pm) {
        this.day29pm = day29pm;
    }

    public String getDay30am() {
        return day30am;
    }

    public void setDay30am(String day30am) {
        this.day30am = day30am;
    }

    public String getDay30pm() {
        return day30pm;
    }

    public void setDay30pm(String day30pm) {
        this.day30pm = day30pm;
    }

    public String getDay31am() {
        return day31am;
    }

    public void setDay31am(String day31am) {
        this.day31am = day31am;
    }

    public String getDay31pm() {
        return day31pm;
    }

    public void setDay31pm(String day31pm) {
        this.day31pm = day31pm;
    }

    public Integer getLatetimes() {
        return latetimes;
    }

    public void setLatetimes(Integer latetimes) {
        this.latetimes = latetimes;
    }

    public Integer getLatetime() {
        return latetime;
    }

    public void setLatetime(Integer latetime) {
        this.latetime = latetime;
    }

    public Integer getAplogy() {
        return aplogy;
    }

    public void setAplogy(Integer aplogy) {
        this.aplogy = aplogy;
    }

    public Double getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(Double absenteeism) {
        this.absenteeism = absenteeism;
    }

    public Double getTrouble() {
        return trouble;
    }

    public void setTrouble(Double trouble) {
        this.trouble = trouble;
    }

    public Double getSick() {
        return sick;
    }

    public void setSick(Double sick) {
        this.sick = sick;
    }

    public Double getYear() {
        return year;
    }

    public void setYear(Double year) {
        this.year = year;
    }

    public Double getSpecial() {
        return special;
    }

    public void setSpecial(Double special) {
        this.special = special;
    }
}
