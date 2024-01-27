import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static LinkedList<LinkedList<String>> states = new LinkedList();
    public static LinkedList<String> symbols = new LinkedList<>();
    public static LinkedList<LinkedList<String>> eClosure = new LinkedList();
    public static LinkedList<LinkedList<String>> movs = new LinkedList<LinkedList<String>>();
    public static String startState;
    public static String[] finalState=new String[999];

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String state = in.nextLine();

        //add states
        {
            while (true) {

                LinkedList zone = new LinkedList();
                zone.add(state.substring(0, state.indexOf(" ")));
                states.add(zone);
                //baadan baraye peyda kardaan e closure behesh niaz darim
                state = state.substring(state.indexOf(" ") + 1);

                if (!state.contains(" ")) {

                    break;

                }
            }
            LinkedList zone = new LinkedList();
            zone.add(state);
            states.add(zone);
            System.out.println(states.toString());

        }

        //start state
        System.out.println("select start state: ");
        startState = in.nextLine();
        //final
        System.out.println("select final states: ");
        finalState[0] = in.nextLine();
        finalState=finalState[0].split(" ");

        //add symbols
        {
            String symbol = in.nextLine();
            {
                while (true) {


                    if (!symbol.contains(" ")) {

                        break;

                    }
                    symbols.add(symbol.substring(0, symbol.indexOf(" ")));
                    symbol = symbol.substring(symbol.indexOf(" ") + 1);

                }
                //symbols.add(symbol);
                System.out.println("syymmmbollls is: " + symbols.toString());
            }
            symbols.add(symbol);
            System.out.println(symbols);
        }
        //add move
        {

            while (true) {

                String move = in.nextLine();
                if (move.equals("end")) {

                    break;

                }
                addMove(move.substring(0, move.indexOf(",")), move.substring(move.indexOf(",") + 1, move.lastIndexOf(",")), move.substring(move.lastIndexOf(",") + 1, move.length()));

            }

        }

        eClosure(startState);
        move();
        System.out.println("states is: " + states);
        System.out.println("eclosure is: " + eClosure);
        System.out.println("movs is: " + movs);
        findFinalState();
        inOut();
    }

    public static void addMove(String state, String symbol, String secState) {

        for (int i = 0; i < states.size(); i++) {

            if (states.get(i).getFirst().equals(state)) {

                states.get(i).add(symbol);
                states.get(i).add(secState);
                break;

            } else if (i == states.size() - 1) {

            }
        }

    }

    public static LinkedList eClosure(String startState) {

        Integer result[] = new Integer[2];
        String statess[] = new String[1000];
        statess = startState.split(" ");
        LinkedList<LinkedList<String>> tempp = copyLinkedList(states);
        Stack position = new Stack();//vase bargashtan b state ghabl
        LinkedList tempEclosure = new LinkedList<String>();
        for (int zz = 0; zz < statess.length; zz++) {

            while (true) {

                if (isArrayNull(result)) {//find start state

                    result = findAny(statess[zz], -1);
                    position.add(result[0]);
                    tempEclosure.add(states.get(result[0]).get(0));

                } else {    //find next state

                    result = findAny((String) states.get((int) result[0]).get((int) result[1]), -1);
                    position.add(result[0]);

                }
                result = findAny("e", result[0]);
                while (isArrayNull(result)) {//vaghti k hich eclosurei toye zone peyda nakardim

                    if (isArrayNull(result) && !position.isEmpty()) {

                        if (!position.isEmpty()) {
                            result = findAny("e", (Integer) position.lastElement());
                            position.pop();

                        }

                    } else if (isArrayNull(result) && position.isEmpty()) {
                        //hazfe tekrari ha
                        for (int i = 0; i < tempEclosure.size() - 1; i++) {


                            for (int j = i + 1; j < tempEclosure.size(); j++) {

                                if (tempEclosure.get(i).toString().compareTo(tempEclosure.get(j).toString()) == 0) {//check kardande

                                    tempEclosure.remove(j);
                                    j--;

                                }

                            }

                        }

                        //sort kardanelist!
                        tempEclosure.stream().sorted();


                        break;

                    }
                }

                if (isArrayNull(result) && position.isEmpty()) {

                    states = copyLinkedList(tempp);
                    break;

                }

                tempEclosure.add(states.get(result[0]).get(result[1] + 1)); //next state
                states.get((int) result[0]).remove((int) result[1]); //delete e closure

            }

        }
        if (eClosure.contains(tempEclosure) == false) {

            eClosure.add(tempEclosure);

        }
        return tempEclosure;
    }


    public static Integer[] findAny(String search, int zone) {

        boolean secBracke = false;

        Integer temp[] = new Integer[2];


        if (zone == -1) {
            for (int i = 0; i < states.size(); i++) {
                if (states.get(i).getFirst().equals(search)) {

//                        secBracke=true;
                    temp[0] = i;
                    temp[1] = 0;//choon get first hast hamoo sefr ro mizarim
                    break;

                }
//                if(secBracke=true) {
//                    break;
//                }

            }
            return temp;
        } else {

            for (int j = 0; j < states.get(zone).size(); j++) {

                if (states.get(zone).get(j).equals(search)) {

                    secBracke = true;
                    temp[0] = zone;
                    temp[1] = j;
                    break;

                }

            }
            return temp;

        }


    }

    public static void move() {

        Integer result[] = new Integer[2];
        String tempA = new String();
        int count;
        for (int zp = 0; zp < eClosure.size(); zp++) {
            for (int j = 0; j < symbols.size(); j++) {

                LinkedList<LinkedList<String>> temppp = copyLinkedList(states);
                LinkedList<String> temp = new LinkedList();
                temp.add(String.valueOf(zp));
                temp.add(symbols.get(j));
                for (int i = 0; i < eClosure.get(zp).size(); i++) {
                    result = findAny(eClosure.get(zp).get(i).toString(), -1);//miad donbale state morede nazar migarde
                    result = findAny((symbols.get(j).toString()), result[0]);//donbal symbol morede nazar toye zone k khat bala pyda kardim migardehhhhh
                    if (!isArrayNull(result)) {

                        count = count(states.get(result[0]), symbols.get(j));
                        states.get((int) result[0]).remove((int) result[1]);//delete mikonim
                        temp.add(states.get((int) result[0]).get((int) result[1]));//chone symbol hazf shode dige result[1]+1 nemizaarim!!!!
                        if(!tempA.isEmpty()) {
                            tempA = tempA +" "+ temp.getLast().toString();
                        } else  {
                            tempA = temp.getLast().toString();
                        }
                        for (int a = 0; a < count - 1; a++) {

                            result = findAny((symbols.get(j).toString()), result[0]);
                            states.get((int) result[0]).remove((int) result[1]);//delete mikonim
                            temp.add(states.get((int) result[0]).get((int) result[1]));
                            tempA = tempA + " " + temp.getLast().toString();

                        }
                    }
                }
                if(!tempA.isEmpty()) {

                    eClosure(tempA);
                    findInMov(eClosure(tempA));
                    temp.add(String.valueOf(findInMov(eClosure(tempA))));
                }
                tempA = "";
                movs.add(temp);
                states = copyLinkedList(temppp);
            }
        }
    }

    public static boolean isArrayNull(Integer[] array) {
        if (array[0] == null) {
            return true;
        } else {
            return false;
        }

    }

    public static LinkedList<LinkedList<String>> copyLinkedList(LinkedList<LinkedList<String>> ll) {
        LinkedList<LinkedList<String>> out = new LinkedList<>();
        for (LinkedList<String> tl : ll) {
            out.add((LinkedList<String>) tl.clone());
        }
        return out;
    }

    public static int count(LinkedList<String> eclow, String search) {

        int a = 0;

        for (int i = 0; i < eclow.size(); i++) {

            if (eclow.get(i).equals(search))
                a++;

        }

        return a;

    }

    public static void inOut() {
        boolean trapIsAvable=false;
        for (int i = 0; i < movs.size(); i++) {


            //baraye taskhise trap ha
            if(movs.get(i).size()>2) {

                System.out.println("(" + movs.get(i).get(0) + ", " + movs.get(i).get(1) + ", " + movs.get(i).getLast() + ")");

            }else {//inja yani bayad trap dashte bashim

                trapIsAvable=true;
                System.out.println("(" + movs.get(i).get(0) + ", " + movs.get(i).get(1) + ", T)");

            }

        }
//        set kardane khode state Trap
        if(trapIsAvable==true){

            for(int i = 0; i < symbols.size(); i++){

                System.out.println("(T, " +symbols.get(i) + ", T)");

            }

        }

    }
    public static int findInMov(LinkedList a) {


        for (int i = 0; i < eClosure.size(); i++) {

            if (eClosure.get(i).equals(a)) {

                return i;

            }

        }
        return -1;
    }

    public static void findFinalState(){
        String out="";
        for(int j=0;j<finalState.length;j++) {//q5 q4

            for (int i = 0; i < eClosure.size(); i++) {

                if (eClosure.get(i).contains(finalState[j])) {
                    out+=String.valueOf(i)+" ";
                }

            }

        }
        System.out.println("Start state is: 0");
        System.out.println("Final state(s) is: "+out);
    }

}