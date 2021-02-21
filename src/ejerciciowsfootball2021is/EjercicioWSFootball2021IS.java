package ejerciciowsfootball2021is;

import https.footballpool_dataaccess.ArrayOftPlayerName;
import https.footballpool_dataaccess.ArrayOftTeamPlayerCardInfo;
import https.footballpool_dataaccess.ArrayOftTeamPlayerGoalsRankInfo;
import https.footballpool_dataaccess.ArrayOftTeamPlayerName;
import https.footballpool_dataaccess.TPlayerName;
import https.footballpool_dataaccess.TTeamPlayerCardInfo;
import https.footballpool_dataaccess.TTeamPlayerGoalsRankInfo;
import https.footballpool_dataaccess.TTeamPlayerName;
import java.util.Scanner;
import org.oorsprong.websamples.ArrayOftCountryCodeAndName;
import org.oorsprong.websamples.TCountryCodeAndName;

public class EjercicioWSFootball2021IS {

    static String country;
    static Scanner scanner;
    public static void main(String[] args) {
        // TODO code application logic here
        scanner = new Scanner(System.in);
        int opcion = 0;
        do{
            showMenu();
            System.out.println("Introduce el número de tu opción:\n");
            opcion = scanner.nextInt();
            
            if(opcion > 0 && opcion <= 4){
                execute(opcion);
            }else if(opcion > 5){
                System.out.println("Introduce un valor dentro de las opciones.");
            }
        }while(opcion != 5);
        System.exit(0);
    }

    private static void showMenu() {
        System.out.println("Bienvenido a la API de DataFlex Football Pool");
        System.out.println("Seleccione una de estas opciones:");
        System.out.println("1 - Nombre de todos los jugadores de un país.");
        System.out.println("2 - Obtener los jugadores de un país por roles.");
        System.out.println("3 - Los jugadores de un país que no tienen tarjeta.");
        System.out.println("4 - Los goleadores de un país.");
        System.out.println("5 - Salir");
    }

    private static void execute(int opcion) {
        switch(opcion){
            case 1:
                showCountry();
                country = scanner.next();
                
                for (TPlayerName tplayer : allPlayerNames(true).getTPlayerName()) {
                    if(tplayer.getSCountryName().toLowerCase().equals(country)){
                        System.out.println("jugador: " + tplayer.getSName());
                    }
                }
                break;
            case 2:
                showCountry();
                country = scanner.next();
                showRoles();
                String role = scanner.next();
                                
                for (TTeamPlayerName tplayer : allPlayersWithRole(country, role).getTTeamPlayerName()) {
                    System.out.println("Jugador: "+tplayer.getSName());
                }
                break;
            case 3:
                showCountry();
                country = scanner.next();
                boolean giveCard = false;
                
                for (TPlayerName tplayer : allPlayerNames(true).getTPlayerName()) {
                    if(tplayer.getSCountryName().toLowerCase().equals(country)){
                        for (TTeamPlayerCardInfo tTeamPlayerCardInfo : allPlayersWithCards().getTTeamPlayerCardInfo()) {
                            if(tTeamPlayerCardInfo.getSName().equals(tplayer.getSName())){
                                giveCard = true;
                                break;
                            }
                        }
                        if(!giveCard){
                            System.out.println("El jugador: " + tplayer.getSName() + " no tiene ninguna tarjeta.");
                            giveCard = false;
                        }
                    }
                }
                break;
            case 4:
                showCountry();
                country = scanner.next();
                
                for (TPlayerName tplayer : allPlayerNames(true).getTPlayerName()) {
                    if(tplayer.getSCountryName().toLowerCase().equals(country)){
                        for (TTeamPlayerGoalsRankInfo tTeamPlayerGoalsRankInfo : playersWithGoalsRanked().getTTeamPlayerGoalsRankInfo()) {
                            if(tplayer.getSName().equals(tTeamPlayerGoalsRankInfo.getSName())){
                                System.out.println("El jugador: " + tplayer.getSName() + " tiene un total de: " + tTeamPlayerGoalsRankInfo.getIGoals() + " goles.");
                            }
                        }
                    }
                }                
                break;
        }
                 
    }

    private static ArrayOftPlayerName allPlayerNames(boolean bSelected) {
        https.footballpool_dataaccess.Info service = new https.footballpool_dataaccess.Info();
        https.footballpool_dataaccess.InfoSoapType port = service.getInfoSoap();
        return port.allPlayerNames(bSelected);
    }

    private static ArrayOftCountryCodeAndName listOfCountryNamesByName() {
        org.oorsprong.websamples.CountryInfoService service = new org.oorsprong.websamples.CountryInfoService();
        org.oorsprong.websamples.CountryInfoServiceSoapType port = service.getCountryInfoServiceSoap();
        return port.listOfCountryNamesByName();
    }

    private static void showCountry() {
        System.out.println("Introduce el nombre del país:");
        for(TCountryCodeAndName tcountry : listOfCountryNamesByName().getTCountryCodeAndName()){
            System.out.println("Código: " + tcountry.getSISOCode() + " País: " +tcountry.getSName());
        }
    }

    private static void showRoles() {
        System.out.println("Portero: Goalkeeper");
        System.out.println("Defensa: Defender");
        System.out.println("Centrocampista: Midfielder");
        System.out.println("Delantero: Forward");
    }

    private static ArrayOftTeamPlayerName allPlayersWithRole(java.lang.String sTeamName, java.lang.String sRoleCode) {
        https.footballpool_dataaccess.Info service = new https.footballpool_dataaccess.Info();
        https.footballpool_dataaccess.InfoSoapType port = service.getInfoSoap();
        return port.allPlayersWithRole(sTeamName, sRoleCode);
    }

    private static ArrayOftTeamPlayerCardInfo allPlayersWithCards() {
        https.footballpool_dataaccess.Info service = new https.footballpool_dataaccess.Info();
        https.footballpool_dataaccess.InfoSoapType port = service.getInfoSoap();
        return port.allPlayersWithCards();
    }

    private static ArrayOftTeamPlayerGoalsRankInfo playersWithGoalsRanked() {
        https.footballpool_dataaccess.Info service = new https.footballpool_dataaccess.Info();
        https.footballpool_dataaccess.InfoSoapType port = service.getInfoSoap();
        return port.playersWithGoalsRanked();
    }
  
}
