package data;


import java.util.HashMap;

public class TestObject {

    private Integer number;
    private String name;
    private HashMap<String, Integer> map;
//    private Image image;

    public TestObject(Integer number, String name, String[] strings, String imageName){
        this.number = number;
        this.name = name;
        map = new HashMap<>();

//        image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
        for (int i = 0; i < strings.length; i++){
            if (! map.containsKey(strings[i])){
                map.put(strings[i], 1);
            }
            else {
                map.put(strings[i], map.get(strings[i]) + 1);
            }
        }
    }

}
