package br.com.Alyson.Controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/math")
public class MathController {
    //http://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo)
            throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) + convertToDouble(numberTwo);


    }
    @RequestMapping("/div/{numberOne}/{numberTwo}")
    public Double div(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo)
            throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) / convertToDouble(numberTwo);


    }
    @RequestMapping("/mult/{numberOne}/{numberTwo}")
    public Double mult(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo)
            throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) * convertToDouble(numberTwo);


    }
    @RequestMapping("/sub/{numberOne}/{numberTwo}")
    public Double sub(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo)
            throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) - convertToDouble(numberTwo);


    }
    @RequestMapping("/raiz/{numberOne}")
    public Double raiz(
            @PathVariable("numberOne") String numberOne
         )
            throws Exception {
        if (!isNumeric(numberOne)) throw new UnsupportedOperationException("Please set a numeric value!");
        double sqrt = Math.sqrt(Double.parseDouble(numberOne));
        return sqrt;


    }


    private Double convertToDouble(String strNumber) throws IllegalArgumentException {
        if (strNumber == null || strNumber.isEmpty())throw new UnsupportedOperationException("Please set a numeric value!");
        String number = strNumber.replace(",",".");
        return Double.parseDouble(number);

    }

    private boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty())return false;
        String number = strNumber.replace(",",".");
      return number.matches("[-+]?[0-9]*\\.?[0-9]+");
     }

    //http://localhost:8080/math/subtraction/3/5
    //http://localhost:8080/math/division/3/5
}
