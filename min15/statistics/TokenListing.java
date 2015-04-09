package min15.statistics;

import analysis.WidthFirstAdapter;
import node.*;
import java.util.*;

/**
 * Created by Antoine-Ali on 08/04/2015.
 * Permanent Code ZARA20069408
 */
public class TokenListing extends WidthFirstAdapter
{

    private void visit(Node node)
    {
        if (node != null) node.apply(this);
    }

    private ArrayList<Token> result = new ArrayList<>();

    public ArrayList<Token> getTokenList(AStmts node)
    {
        result = new ArrayList<>();
        visit(node);

        return result;
    }


//region stmts
    @Override
    public void caseAStmts(AStmts node)
    {
        for(Node n : node.getStmts())
        {
            visit(n);
        }
    }
//endregion

//region stmt
    @Override
    public void caseAVarDefStmt(AVarDefStmt node)
    {
        result.addAll(Arrays.asList(node.getVar(), node.getId(), node.getColon(), node.getClassName()));
    }

    @Override
    public void caseAVarInitStmt(AVarInitStmt node)
    {
        result.addAll(Arrays.asList(node.getVar(), node.getId(), node.getEq()));
        visit(node.getExp());
    }

    @Override
    public void caseAVarAssignStmt(AVarAssignStmt node)
    {
        result.addAll(Arrays.asList(node.getId(), node.getEq()));
        visit(node.getExp());
    }

    @Override
    public void caseAFieldAssignStmt(AFieldAssignStmt node)
    {
        result.addAll(Arrays.asList(node.getFieldName(), node.getEq()));
        visit(node.getExp());
    }

    @Override
    public void caseACallStmt(ACallStmt node)
    {
        visit(node.getCall());
    }

    @Override
    public void caseASelfCallStmt(ASelfCallStmt node)
    {
        visit(node.getSelfCall());
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node)
    {
        result.add(node.getWhile());
        visit(node.getExp());
        result.add(node.getDo());
        visit(node.getStmts());
        result.add(node.getEnd());
    }

    @Override
    public void caseAIfStmt(AIfStmt node)
    {
        result.add(node.getIf());
        visit(node.getExp());
        result.add(node.getThen());
        visit(node.getStmts());
        visit(node.getElsePart());
        result.add(node.getEnd());
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node)
    {
        result.add(node.getReturn());
        visit(node.getExp());
    }
//endregion

//region    else_part =
//        else eol stmts;

    @Override
    public void caseAElsePart(AElsePart node)
    {
        result.add(node.getElse());
        visit(node.getStmts());
    }
//endregion

//region    exp =
//    {or} exp or eol? conjunction |
//    {simple} conjunction;

    @Override
    public void caseAOrExp(AOrExp node)
    {
        visit(node.getExp());
        result.add(node.getOr());
        visit(node.getConjunction());
    }

    @Override
    public void caseASimpleExp(ASimpleExp node)
    {
        visit(node.getConjunction());
    }
//endregion

//region    conjunction =
//    {and} conjunction and eol? comparison |
//    {simple} comparison;

    @Override
    public void caseAAndConjunction(AAndConjunction node)
    {
        visit(node.getConjunction());
        result.add(node.getAnd());
        visit(node.getComparison());
    }

    @Override
    public void caseASimpleConjunction(ASimpleConjunction node)
    {
        visit(node.getComparison());
    }
//endregion

//region    comparison =
//    {eq} comparison eq eol? arith_exp |
//    {neq} comparison neq eol? arith_exp |
//    {lt} comparison lt eol? arith_exp |
//    {gt} comparison gt eol? arith_exp |
//    {lteq} comparison lteq eol? arith_exp |
//    {gteq} comparison gteq eol? arith_exp |
//    {is} comparison is eol? arith_exp |
//    {simple} arith_exp;

    @Override
    public void caseAEqComparison(AEqComparison node)
    {
        visit(node.getComparison());
        result.add(node.getEq());
        visit(node.getArithExp());
    }

    @Override
    public void caseANeqComparison(ANeqComparison node)
    {
        visit(node.getComparison());
        result.add(node.getNeq());
        visit(node.getArithExp());
    }

    @Override
    public void caseALtComparison(ALtComparison node)
    {
        visit(node.getComparison());
        result.add(node.getLt());
        visit(node.getArithExp());
    }

    @Override
    public void caseAGtComparison(AGtComparison node)
    {
        visit(node.getComparison());
        result.add(node.getGt());
        visit(node.getArithExp());
    }

    @Override
    public void caseALteqComparison(ALteqComparison node)
    {
        visit(node.getComparison());
        result.add(node.getLteq());
        visit(node.getArithExp());
    }

    @Override
    public void caseAGteqComparison(AGteqComparison node)
    {
        visit(node.getComparison());
        result.add(node.getGteq());
        visit(node.getArithExp());
    }

    @Override
    public void caseAIsComparison(AIsComparison node)
    {
        visit(node.getComparison());
        result.add(node.getIs());
        visit(node.getArithExp());
    }

    @Override
    public void caseASimpleComparison(ASimpleComparison node)
    {
        visit(node.getArithExp());
    }
//endregion

//region    arith_exp =
//    {add} arith_exp plus eol? factor |
//    {sub} arith_exp minus eol? factor |
//    {simple} factor;

    @Override
    public void caseAAddArithExp(AAddArithExp node)
    {
        visit(node.getArithExp());
        result.add(node.getPlus());
        visit(node.getFactor());
    }

    @Override
    public void caseASubArithExp(ASubArithExp node)
    {
        visit(node.getArithExp());
        result.add(node.getMinus());
        visit(node.getFactor());
    }

    @Override
    public void caseASimpleArithExp(ASimpleArithExp node)
    {
        visit(node.getFactor());
    }


//endregion

//region    factor =
//    {mul} factor star eol? left_unary_exp |
//    {div} factor slash eol? left_unary_exp |
//    {mod} factor percent eol? left_unary_exp |
//    {simple} left_unary_exp;

    @Override
    public void caseAMulFactor(AMulFactor node)
    {
        visit(node.getFactor());
        result.add(node.getStar());
        visit(node.getLeftUnaryExp());

    }

    @Override
    public void caseADivFactor(ADivFactor node)
    {
        visit(node.getFactor());
        result.add(node.getSlash());
        visit(node.getLeftUnaryExp());
    }

    @Override
    public void caseAModFactor(AModFactor node)
    {
        visit(node.getFactor());
        result.add(node.getPercent());
        visit(node.getLeftUnaryExp());
    }

    @Override
    public void caseASimpleFactor(ASimpleFactor node)
    {
        visit(node.getLeftUnaryExp());
    }


//endregion

//region    left_unary_exp =
//    {not} not left_unary_exp |
//    {neg} minus left_unary_exp |
//    {simple} right_unary_exp;

    @Override
    public void caseANotLeftUnaryExp(ANotLeftUnaryExp node)
    {
        result.add(node.getNot());
        visit(node.getLeftUnaryExp());
    }

    @Override
    public void caseANegLeftUnaryExp(ANegLeftUnaryExp node)
    {
        result.add(node.getMinus());
        visit(node.getLeftUnaryExp());
    }

    @Override
    public void caseASimpleLeftUnaryExp(ASimpleLeftUnaryExp node)
    {
        visit(node.getRightUnaryExp());
    }


//endregion

//region    right_unary_exp =
//    {call} call |
//    {isa} right_unary_exp isa class_name |
//    {as} right_unary_exp as class_name |
//    {simple} term;

    @Override
    public void caseACallRightUnaryExp(ACallRightUnaryExp node)
    {
        visit(node.getCall());
    }

    @Override
    public void caseAIsaRightUnaryExp(AIsaRightUnaryExp node)
    {
        visit(node.getRightUnaryExp());
        result.addAll(Arrays.asList(node.getIsa(), node.getClassName()));
    }

    @Override
    public void caseAAsRightUnaryExp(AAsRightUnaryExp node)
    {
        visit(node.getRightUnaryExp());
        result.addAll(Arrays.asList(node.getAs(), node.getClassName()));
    }

    @Override
    public void caseASimpleRightUnaryExp(ASimpleRightUnaryExp node)
    {
        visit(node.getTerm());
    }


//endregion

//region    term =
//    {self_call} self_call |
//    {par} l_par [eol1]:eol? exp [eol2]:eol? r_par |
//    {new} new class_name |
//    {field} field_name |
//    {var} id |
//    {num} number |
//    {null} null |
//    {self} self |
//    {true} true |
//    {false} false |
//    {string} string;

    @Override
    public void caseASelfCallTerm(ASelfCallTerm node)
    {
        visit(node.getSelfCall());
    }

    @Override
    public void caseAParTerm(AParTerm node)
    {
        result.add(node.getLPar());
        visit(node.getExp());
        result.add(node.getRPar());
    }

    @Override
    public void caseANewTerm(ANewTerm node)
    {
        result.addAll(Arrays.asList(node.getNew(), node.getClassName()));
    }

    @Override
    public void caseAFieldTerm(AFieldTerm node)
    {
        result.add(node.getFieldName());
    }

    @Override
    public void caseAVarTerm(AVarTerm node)
    {
        result.add(node.getId());
    }

    @Override
    public void caseANumTerm(ANumTerm node)
    {
        result.add(node.getNumber());
    }

    @Override
    public void caseANullTerm(ANullTerm node)
    {
        result.add(node.getNull());
    }

    @Override
    public void caseASelfTerm(ASelfTerm node)
    {
       result.add(node.getSelf());
    }

    @Override
    public void caseATrueTerm(ATrueTerm node)
    {
        result.add(node.getTrue());
    }

    @Override
    public void caseAFalseTerm(AFalseTerm node)
    {
        result.add(node.getFalse());
    }

    @Override
    public void caseAStringTerm(AStringTerm node)
    {
        result.add(node.getString());
    }


//endregion

//region    call =
//    right_unary_exp dot eol? id l_par args? r_par;

    @Override
    public void caseACall(ACall node)
    {
        visit(node.getRightUnaryExp());
        result.addAll(Arrays.asList(node.getDot(), node.getId(), node.getLPar()));
        visit(node.getArgs());
        result.add(node.getRPar());
    }


//endregion

//region    self_call =
//    id l_par args? r_par;

    @Override
    public void caseASelfCall(ASelfCall node)
    {
        result.addAll(Arrays.asList(node.getId(), node.getLPar()));
        visit(node.getArgs());
        result.add(node.getRPar());
    }


//endregion

//region    args =
//        [eol1]:eol? arg [additional_args]:additional_arg* [eol2]:eol?;

    @Override
    public void caseAArgs(AArgs node)
    {
        visit(node.getArg());
        for(Node n : node.getAdditionalArgs())
        {
            visit(n);
        }
    }


//endregion

//region    additional_arg =
//    comma eol? arg;

    @Override
    public void caseAAdditionalArg(AAdditionalArg node)
    {
        result.add(node.getComma());
        visit(node.getArg());
    }

//endregion

//region    arg =
//    exp;

    @Override
    public void caseAArg(AArg node)
    {
        visit(node.getExp());
    }


//endregion
}
