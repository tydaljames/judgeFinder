import java.util.ArrayList;

public class Judge {
    private int[][] trust;
    private ArrayList<Integer> people = new ArrayList<>();
    private ArrayList<Integer> nonTrust = new ArrayList<>();
    private int n = 0;
    private int e = 0;


    public Judge(int[][] trust){ //Constructs a Judge object using a 2d array of trust pairs
        this.trust = trust;
    }


    public void buildTrust(){ //Runs all necessary functions required before findJudge is called
        countEm();

        if(trust.length>e) {
            shrinkArray();
        }

        if(n<1 || n>1000){
            System.out.println("Must have the appropriate amount of people: \"1-1000\"");
            System.exit(0);
        }
        System.out.println(n + " people, " + e + " entries");
    }


    public void countEm(){//Finds the value of n (# of people) and e (# of trust pairs)
        int a, b;

        for(int i=0;i<trust.length;i++){
           a = trust[i][0];
           b = trust[i][1];
           if(a != 0 && b != 0){
                e++;

                if(people.contains(a) == false){
                    people.add(a);
                    n++;
                }
               if(people.contains(b) == false){
                   people.add(b);
                   n++;
               }
                }
            }
    }


    public void shrinkArray(){ //Shrinks the trust array to the number of supplied trust pairs, speeding up future search times.
        int[][] temp = new int[e][2];
        int a, b;
        int i = 0;
        int k = 0; //increments when a valid trust pair is added to the new temp array

        while(k<e){
            a = trust[i][0];
            b = trust[i][1];
            if(a != 0 && b != 0){
                temp[k][0] = a;
                temp[k][1] = b;
                k++;
            }
            i++;
        }
        trust = temp;
    }


    public void nonTrust(){ //Checks for all people who don't trust anyone, and adds them to a list of judge candidates
        int a, b, p;
        ArrayList<Integer> trusting = new ArrayList<>(); //Array containing all people who trust anyone

        for(int i=0;i<e;i++) { //Checks all people and adds them to "trusting" if they trust anyone
            a = trust[i][0];
            b = trust[i][1];
            if(trusting.contains(a) == false && (a != b)){
                trusting.add(a);
            }
        }

        for(int i=0;i<n;i++){ //Searches for all people who don't trust anyone, and adds them to "nonTrust"
            p = people.get(i);
            if(trusting.contains(p) == false){
                nonTrust.add(p);
            }
        }
    }


    public boolean checkTrust(int J){ //Checks Judge candidate (p) to see if everyone trusts them.
        int a, b;
        int trustcount = 1;
        ArrayList<Integer> trusting = new ArrayList<>(); //Array containing all people who trust the Judge candidate

        trusting.add(J);

        for(int i=0;i<e;i++){
            a = trust[i][0];
            b = trust[i][1];

            if(b == J && trusting.contains(a) == false){
                trusting.add(a);
                trustcount++;
            }

            if(n==trustcount){ //If each person trusts the candidate, they are the Judge
                return true;
            }
        }
            return false; //If all trust pairs are checked and not everyone trusts the candidate, they are not the Judge
    }


    public int findJudge (int n, int[][] trust){ //Find the Judge and returns their number if true, returns -1 if false
        int J;

        nonTrust(); //Checks for all people who don't trust anyone, as potential Judges

        if(nonTrust.size() == 1) { //Checks Judge candidates for trust from all others and returns their number if true
            J = nonTrust.get(0);   //A Judge is only possible if the size of nonTrust = 1
            if(checkTrust(J) == true){
                return J;
            }
        }

        return -1; //Returns -1 if no Judges are found
        }


    public void run(){ //Runs all functions and prints the result
        buildTrust();

        int Judge = findJudge(n,trust);

        if(Judge == -1){
            System.out.println("There is no Judge\n-1");
        }
        else {
            System.out.println("The judge is citizen #" + Judge);
        }

        }


    public static void main(String[] args){
        int[][] trust = {{1,3},{1,4},{2,3},{2,4},{4,3}};
        Judge j = new Judge(trust);
        j.run();

    }
}


