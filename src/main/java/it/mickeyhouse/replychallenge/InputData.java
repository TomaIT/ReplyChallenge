package it.mickeyhouse.replychallenge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Data
@NoArgsConstructor
public class InputData {
    private Place[][] floor;
    private TreeSet<Person> persons=new TreeSet<>();

    private int[][] nearPersons;

    private LinkedList<Developer> developers=new LinkedList<>();
    private LinkedList<Manager> managers=new LinkedList<>();
    private LinkedList<Place> placesDevMan = new LinkedList<>();
    private LinkedList<PlacePair> placePairsDD = new LinkedList<>();
    private LinkedList<PlacePair> placePairsMM = new LinkedList<>();
    private LinkedList<PlacePair> placePairsDM_MD = new LinkedList<>();

    private PriorityQueue<ItemPQ> pQueue = new PriorityQueue<>(1000*1000,(a,b)->b.getScore()-a.getScore());

    public InputData(String fileName) throws IOException {
        this.loadDataFromFile(fileName);
    }

    public void loadDataFromFile(String fileName) throws IOException {
        File fileDir = new File(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), StandardCharsets.UTF_8));
        String str;
        str = in.readLine();
        int row=Integer.parseInt(str.split(" ")[1]);
        int col=Integer.parseInt(str.split(" ")[0]);
        floor=new Place[row][col];
        for(int i=0;i<row;i++){
            int j=0;
            str = in.readLine();
            for(char c : str.toCharArray()){
                floor[i][j]=new Place(c,j,i);
                if(c == '_' || c == 'M') placesDevMan.add(floor[i][j]);
                j++;
                if(j>=col)break;
            }
        }

        int nDev=Integer.parseInt(in.readLine());
        for(int i=0;i<nDev;i++){
            Developer d=new Developer();
            d.setOrderFile(persons.size());
            String[] s=in.readLine().split(" ");
            d.setSociety(s[0]);
            d.setBonus(Integer.parseInt(s[1]));
            int n=Integer.parseInt(s[2]);
            for(int j=3;j<3+n;j++){
                d.addSkill(s[j]);
            }
            persons.add(d);
            developers.add(d);
        }

        int nM=Integer.parseInt(in.readLine());
        for(int i=0;i<nM;i++){
            Manager d=new Manager();
            d.setOrderFile(persons.size());
            String[] s=in.readLine().split(" ");
            d.setSociety(s[0]);
            d.setBonus(Integer.parseInt(s[1]));
            persons.add(d);
            managers.add(d);
        }
        in.close();

        nearPersons = new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(floor[i][j].getType() == '#'){
                    floor[i][j].setNearPersons(-1);
                    nearPersons[i][j]=-1;
                }
                else{
                    floor[i][j].setNearPersons(0);
                    nearPersons[i][j]=0;
                }
            }
        }
    }

    public void initPQueue(){
        Map<String, List<Person>> personsPerSoc = persons.stream()
                .collect(groupingBy(Person::getSociety));

        personsPerSoc.entrySet().stream().forEach(x->{
            for(Person a : x.getValue()){
                for (Person b : x.getValue() ){
                    if(a.equals(b))continue;
                    pQueue.add(new PeoplePair(a,b));
                }
            }
        });
    }

    public void initPlacePairs(){
        for(int i=0;i<floor.length;i++){
            for(int j=0;j<floor[i].length;j++){
                char a = floor[i][j].getType();
                if( i+1 < floor.length){
                    char b = floor[i+1][j].getType();
                    if( a == b && a == '_' )placePairsDD.add(new PlacePair(floor[i][j],floor[i+1][j],PlacePairType.DD));
                    if( a == b && a == 'M' )placePairsMM.add(new PlacePair(floor[i][j],floor[i+1][j],PlacePairType.MM));
                    if( a == '_' && b == 'M' )placePairsDM_MD.add(new PlacePair(floor[i][j],floor[i+1][j],PlacePairType.DM));
                    if( a == 'M' && b == '_' )placePairsDM_MD.add(new PlacePair(floor[i][j],floor[i+1][j],PlacePairType.MD));
                }
                if( j+1 < floor[i].length){
                    char b = floor[i][j+1].getType();
                    if( a == b && a == '_' )placePairsDD.add(new PlacePair(floor[i][j],floor[i][j+1],PlacePairType.DD));
                    if( a == b && a == 'M' )placePairsMM.add(new PlacePair(floor[i][j],floor[i][j+1],PlacePairType.MM));
                    if( a == '_' && b == 'M' )placePairsDM_MD.add(new PlacePair(floor[i][j],floor[i][j+1],PlacePairType.DM));
                    if( a == 'M' && b == '_' )placePairsDM_MD.add(new PlacePair(floor[i][j],floor[i][j+1],PlacePairType.MD));
                }
            }
        }
        Collections.shuffle(placePairsDD);
        Collections.shuffle(placePairsMM);
        Collections.shuffle(placePairsDM_MD);
    }

    private void myRoutine(boolean updatePeoplePlace){
        while(!pQueue.isEmpty()){
            ItemPQ itemPQ = pQueue.poll();
            if( itemPQ instanceof PeoplePair ){
                PeoplePair e = (PeoplePair) itemPQ;
                if( e.a.isPlaced() && e.b.isPlaced() )continue;
                if( !e.a.isPlaced() && !e.b.isPlaced() ){
                    Place[] p = findTwoPlacesFree( e.a.getType(), e.b.getType());
                    if(p == null)continue;
                    places(e.a,p[0],updatePeoplePlace);
                    places(e.b,p[1],updatePeoplePlace);
                    continue;
                }
                if( e.a.isPlaced() && !e.b.isPlaced() ){
                    Place p = findNearPlaceFree(e.a,e.b.getType());
                    if(p == null)continue;
                    places(e.b,p,updatePeoplePlace);
                    continue;
                }
                if( !e.a.isPlaced() && e.b.isPlaced() ){
                    Place p = findNearPlaceFree(e.b,e.a.getType());
                    if(p == null)continue;
                    places(e.a,p,updatePeoplePlace);
                    continue;
                }
            }
            if( itemPQ instanceof PeoplePlace ){
                PeoplePlace p = (PeoplePlace) itemPQ;
                if( p.place.getPerson() == null && p.place.getType() == p.person.getType() && !p.person.isPlaced()){
                    places(p.person,p.place,updatePeoplePlace);
                }
            }

        }
    }

    public void findSolution(){
        myRoutine(true);
    }

    public void finalFillPlaces(){
        placesDevMan.stream().filter(x->x.getType()=='_'&&x.getPerson()==null).forEach(place->{
            developers.stream().filter(x->!x.isPlaced()).forEach(person->{
                pQueue.add(new PeoplePlace(floor,person,place));
            });
        });
        placesDevMan.stream().filter(x->x.getType()=='M'&&x.getPerson()==null).forEach(place->{
            managers.stream().filter(x->!x.isPlaced()).forEach(person->{
                pQueue.add(new PeoplePlace(floor,person,place));
            });
        });
        myRoutine(false);
    }

    public void places(Person a,Place place,boolean update){
        a.setPlaced(true);
        a.setXPosition(place.getX());
        a.setYPosition(place.getY());
        place.setPerson(a);

        if(update)updateNearPersons(a,place.getY(),place.getX(),3);

    }

    public void updatePeoplePlace(Person p, Place place){
        pQueue.addAll(persons.stream().filter(x-> !x.isPlaced() && x.getType() == place.getType()
                && x.getSociety().equals(p.getSociety())
        ).map(x->new PeoplePlace(floor,x,place)).collect(Collectors.toList()));
    }

    public void updateNearPersons(Person p, int i,int j,int nNearToUpdatePeoplePlace){
        nearPersons[i][j] = -1;
        floor[i][j].setNearPersons(-1);

        if( i+1 < floor.length && nearPersons[i+1][j] >= 0 ) {
            nearPersons[i + 1][j]++;
            floor[i+1][j].incNP();
            if( nearPersons[i+1][j] >= nNearToUpdatePeoplePlace )
                updatePeoplePlace(p,floor[i+1][j]);
        }
        if( j+1 < floor[i].length && nearPersons[i][j+1] >= 0 ) {
            nearPersons[i][j + 1]++;
            floor[i][j+1].incNP();
            if( nearPersons[i][j+1] >= nNearToUpdatePeoplePlace )
                updatePeoplePlace(p,floor[i][j+1]);
        }
        if( i-1 >= 0 && nearPersons[i-1][j] >= 0 ) {
            nearPersons[i - 1][j]++;
            floor[i-1][j].incNP();
            if( nearPersons[i-1][j] >= nNearToUpdatePeoplePlace )
                updatePeoplePlace(p,floor[i-1][j]);
        }
        if( j-1 >= 0 && nearPersons[i][j-1] >= 0 ) {
            nearPersons[i][j - 1]++;
            floor[i][j-1].incNP();
            if( nearPersons[i][j-1] >= nNearToUpdatePeoplePlace )
                updatePeoplePlace(p,floor[i][j-1]);
        }
    }

    public Place[] findTwoPlacesFree(char a,char b){
        Place[] ret = new Place[2];
        if( a == b && b == '_'){
            while (!placePairsDD.isEmpty()){
                PlacePair p = placePairsDD.pop();
                if( p.getA().getPerson() == null && p.getB().getPerson() == null ) {
                    ret[0] = p.getA();
                    ret[1] = p.getB();
                    return ret;
                }
            }
            return null;
        }
        if( a == b && b == 'M'){
            while (!placePairsMM.isEmpty()){
                PlacePair p = placePairsMM.pop();
                if( p.getA().getPerson() == null && p.getB().getPerson() == null ) {
                    ret[0] = p.getA();
                    ret[1] = p.getB();
                    return ret;
                }
            }
            return null;
        }
        if( a == '_' && b == 'M') {
            while (!placePairsDM_MD.isEmpty()){
                PlacePair p = placePairsDM_MD.pop();
                if( p.getA().getPerson() == null && p.getB().getPerson() == null ) {
                    if(p.getType() == PlacePairType.DM) {
                        ret[0] = p.getA();
                        ret[1] = p.getB();
                    }else{
                        ret[0] = p.getB();
                        ret[1] = p.getA();
                    }
                    return ret;
                }
            }
            return null;
        }

        if( a == 'M' && b == '_') {
            while (!placePairsDM_MD.isEmpty()){
                PlacePair p = placePairsDM_MD.pop();
                if( p.getA().getPerson() == null && p.getB().getPerson() == null ) {
                    if(p.getType() == PlacePairType.MD) {
                        ret[0] = p.getA();
                        ret[1] = p.getB();
                    }else{
                        ret[0] = p.getB();
                        ret[1] = p.getA();
                    }
                    return ret;
                }
            }
        }
        return null;
    }

    public Place findNearPlaceFree(Person start,char dest){
        int i = start.getYPosition();
        int j = start.getXPosition();

        List<Place> places = new ArrayList<>();
        if( i+1 < floor.length && floor[i+1][j].getPerson() == null && floor[i+1][j].getType() == dest ){
            places.add(floor[i+1][j]);
            //return floor[i+1][j];
        }
        if(i-1 >= 0 && floor[i-1][j].getPerson() == null && floor[i-1][j].getType() == dest ){
            places.add(floor[i-1][j]);
            //return floor[i-1][j];
        }
        if(j+1 < floor[i].length && floor[i][j+1].getPerson() == null && floor[i][j+1].getType() == dest){
            places.add(floor[i][j+1]);
            //return floor[i][j+1];
        }
        if(j-1 >= 0 && floor[i][j-1].getPerson() == null && floor[i][j-1].getType() == dest){
            places.add(floor[i][j-1]);
            //return floor[i][j-1];
        }
        if(places.size()<=0)return null;
        places.sort(Comparator.comparingInt(Place::getNearPersons));
        return places.get(0);
    }

    public void statistics(){
        System.out.println("Dev Places Free: "+placesDevMan.stream().filter(x->x.getType()=='_'&&x.getPerson()==null).count());
        System.out.println("Man Places Free: "+placesDevMan.stream().filter(x->x.getType()=='M'&&x.getPerson()==null).count());
        System.out.println("Dev Free: "+developers.stream().filter(x->!x.isPlaced()).count());
        System.out.println("Man Free: "+managers.stream().filter(x->!x.isPlaced()).count());
    }

    private void swap(Person a,Person b){
        int tx=a.getXPosition(),ty=a.getYPosition();
        floor[ty][tx].setPerson(b);
        floor[b.getYPosition()][b.getXPosition()].setPerson(a);
        a.setXPosition(b.getXPosition());
        a.setYPosition(b.getYPosition());
        b.setYPosition(ty);
        b.setXPosition(tx);
    }

    private void swapAssigning(Person a,Person b){
        int tx=a.getXPosition(),ty=a.getYPosition();
        floor[ty][tx].setPerson(b);
        a.setXPosition(-1);
        a.setYPosition(-1);
        a.setPlaced(false);
        b.setYPosition(ty);
        b.setXPosition(tx);
        b.setPlaced(true);
    }

    public void checkScoreSwap(Person a,Person b){
        if(a.getType() != b.getType())return;
        if( a.isPlaced() && b.isPlaced() ){
            int oldScore = getScore(floor[a.getYPosition()][a.getXPosition()],a) +
                    getScore(floor[b.getYPosition()][b.getXPosition()],b);
            swap(a,b);
            int newScore = getScore(floor[a.getYPosition()][a.getXPosition()],a) +
                    getScore(floor[b.getYPosition()][b.getXPosition()],b);
            if(newScore >= oldScore) return;
            swap(a,b);
        }
        if( a.isPlaced() && !b.isPlaced() ){
            int oldScore = getScore(floor[a.getYPosition()][a.getXPosition()],a);
            swapAssigning(a,b);
            int newScore = getScore(floor[b.getYPosition()][b.getXPosition()],b);
            if(newScore >= oldScore) return;
            swapAssigning(b,a);
        }
        if( !a.isPlaced() && b.isPlaced() ){
            int oldScore = getScore(floor[b.getYPosition()][b.getXPosition()],b);
            swapAssigning(b,a);
            int newScore = getScore(floor[a.getYPosition()][a.getXPosition()],a);
            if(newScore >= oldScore) return;
            swapAssigning(a,b);
        }
    }

    private int getScore(Place place,Person a){
        int i = place.getY();
        int j = place.getX();
        int score = 0;

        if( i+1 < floor.length) score += a.getBP(floor[i+1][j].getPerson()) + a.getWP(floor[i+1][j].getPerson());
        if( j+1 < floor[i].length) score += a.getBP(floor[i][j+1].getPerson()) + a.getWP(floor[i][j+1].getPerson());
        if( i-1 >= 0 ) score += a.getBP(floor[i-1][j].getPerson()) + a.getWP(floor[i-1][j].getPerson());
        if( j-1 >= 0 ) score += a.getBP(floor[i][j-1].getPerson()) + a.getWP(floor[i][j-1].getPerson());

        return score;
    }
}