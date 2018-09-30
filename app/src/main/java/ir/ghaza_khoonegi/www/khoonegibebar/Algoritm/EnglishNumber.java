package ir.ghaza_khoonegi.www.khoonegibebar.Algoritm;

public class EnglishNumber {
    public Integer getNumber(String number)
    {
        String[] persian ={ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        String state;
        String pnumber="";
        number=number.replace(" ","");
        number=number.replace(",","");
        int j=0;
        for (int i = number.length()-1; i >=0; i--) {
            state=number.substring(i,i+1);
            switch (state){
                case "۰":
                    pnumber=persian[0]+pnumber;
                    break;
                case "۱":
                    pnumber=persian[1]+pnumber;
                    break;
                case "۲":
                    pnumber=persian[2]+pnumber;
                    break;
                case "۳":
                    pnumber=persian[3]+pnumber;
                    break;
                case "۴":
                    pnumber=persian[4]+pnumber;
                    break;
                case "۵":
                    pnumber=persian[5]+pnumber;
                    break;
                case "۶":
                    pnumber=persian[6]+pnumber;
                    break;
                case "۷":
                    pnumber=persian[7]+pnumber;
                    break;
                case "۸":
                    pnumber=persian[8]+pnumber;
                    break;
                case "۹":
                    pnumber=persian[9]+pnumber;
                    break;
                default:
                    break;
            }
        }
        return Integer.parseInt(pnumber);
    }
}
