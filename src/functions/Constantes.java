package functions;

public class Constantes {

    public enum BaseDeDatos {
        DBNAME("RuedaEnLaETITC"),
        TABLEBICICLETAS("BicicletaNormal"),
        TABLEADMIN("Administradores"),
        TABLEESTUDIANTES("Estudiantes"),
        TABLESEGURIDAD("Seguridad"),
        TABLEPARQUEADEROS("ParqueaderoSedes"),
        DRIVER("com.mysql.cj.jdbc.Driver"),
        USER("root"),
        PASSWORD("dbmysql2022"),
        URL("jdbc:mysql://localhost:3306/ ");

        public final String value;

        private BaseDeDatos(String str) {
            this.value = str;
        }
    }

    public enum listaRoles {
        Administrador,
        Estudiante,
        Seguridad
    }

    public enum listaSedes {
        CARVAJAL,
        CENTRO,
        TINTAL
    }
    
    public enum listaSedesDesarrollo {
        ESTUDIANTE,
        CARVAJAL,
        CENTRO,
        TINTAL
    }

    public enum ListaEstadosEstudiante {
        ACTIVO,
        INACTIVO,   //Se asigna a todos cuando termina el semestre
        SANCIONADO
    }

    public enum ListaCarreras {
        ELECTROMECÁNICA,
        PINDUSTRIALES,
        SISTEMAS,
        MECATRÓNICA,
        MECÁNICA
    }

    public enum tipoSancion {
        DEUDA,
        RESTRICCION
    }

    public enum ListaTiposBicicletas {
        NORMAL
    }

    public enum ListaEstadoBicicletaYPartes {
        FUNCIONAL,
        NOFUNCIONAL
    }

    public enum TiposFreno {
        ARO,
        DISCO
    }

    public enum MaterialManubrio {
        ALEACION,
        ACEROINOXIDABLE,
        ALUMINIO
    }

    public enum MaterialMarco {
        ALUMINIO,
        ACERO
    }

    public enum MaterialPedales {
        ALUMINIO,
        PLASTICO
    }

    public enum MotoresBicicleta {
        TRACCIONHUMANA,
        ELECTRICA
    }
}
