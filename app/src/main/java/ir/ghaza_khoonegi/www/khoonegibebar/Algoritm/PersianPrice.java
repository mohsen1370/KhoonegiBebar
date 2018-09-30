package ir.ghaza_khoonegi.www.khoonegibebar.Algoritm;

public class PersianPrice {

    public String getNumber(String number)
    {
        number=number.replace(" ","");
        if (number.equals("")){
            return "۰";
        }
        String[] persian ={ "۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹" };
        String state;
        String pnumber="";
        int j=0;
        for (int i = number.length()-1; i >=0; i--) {
            state=number.substring(i,i+1);
            switch (state){
                case "0":
                    pnumber=persian[0]+pnumber;
                    break;
                case "1":
                    pnumber=persian[1]+pnumber;
                    break;
                case "2":
                    pnumber=persian[2]+pnumber;
                    break;
                case "3":
                    pnumber=persian[3]+pnumber;
                    break;
                case "4":
                    pnumber=persian[4]+pnumber;
                    break;
                case "5":
                    pnumber=persian[5]+pnumber;
                    break;
                case "6":
                    pnumber=persian[6]+pnumber;
                    break;
                case "7":
                    pnumber=persian[7]+pnumber;
                    break;
                case "8":
                    pnumber=persian[8]+pnumber;
                    break;
                case "9":
                    pnumber=persian[9]+pnumber;
                    break;
                case "-":
                    pnumber="-"+pnumber;
                    break;
                default:
                    break;
            }
        }
        return pnumber;
    }
    public String getPrice(String price){
        price=price.replace(" ","");
        if (price.equals("")){
            return "۰";
        }
        String[] persian ={ "۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹" };
        String state;
        String pricep="";
        int j=0;
        for (int i = price.length()-1; i >=0; i--) {
            if(j==3){
                pricep=","+pricep;
                j=0;
            }
            state=price.substring(i,i+1);
            switch (state){
                case "0":
                    pricep=persian[0]+pricep;
                    break;
                case "1":
                    pricep=persian[1]+pricep;
                    break;
                case "2":
                    pricep=persian[2]+pricep;
                    break;
                case "3":
                    pricep=persian[3]+pricep;
                    break;
                case "4":
                    pricep=persian[4]+pricep;
                    break;
                case "5":
                    pricep=persian[5]+pricep;
                    break;
                case "6":
                    pricep=persian[6]+pricep;
                    break;
                case "7":
                    pricep=persian[7]+pricep;
                    break;
                case "8":
                    pricep=persian[8]+pricep;
                    break;
                case "9":
                    pricep=persian[9]+pricep;
                    break;
                default:
                    break;
            }
            j++;
        }
        return pricep;
    }
}
