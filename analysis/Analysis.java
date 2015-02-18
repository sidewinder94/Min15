/* This file was generated by SableCC (http://www.sablecc.org/). */

package analysis;

import node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAFile(AFile node);
    void caseAClassDef(AClassDef node);
    void caseASuperDecl(ASuperDecl node);
    void caseAFieldMember(AFieldMember node);
    void caseAMethodMember(AMethodMember node);
    void caseAOperatorMember(AOperatorMember node);
    void caseAInternMethodMember(AInternMethodMember node);
    void caseAInternOperatorMember(AInternOperatorMember node);
    void caseAParams(AParams node);
    void caseAAdditionalParam(AAdditionalParam node);
    void caseAParam(AParam node);
    void caseAReturnDecl(AReturnDecl node);
    void caseAEqOperator(AEqOperator node);
    void caseANeqOperator(ANeqOperator node);
    void caseALtOperator(ALtOperator node);
    void caseAGtOperator(AGtOperator node);
    void caseALteqOperator(ALteqOperator node);
    void caseAGteqOperator(AGteqOperator node);
    void caseAPlusOperator(APlusOperator node);
    void caseAMinusOperator(AMinusOperator node);
    void caseAStarOperator(AStarOperator node);
    void caseASlashOperator(ASlashOperator node);
    void caseAPercentOperator(APercentOperator node);
    void caseAStmts(AStmts node);
    void caseAEmptyStmt(AEmptyStmt node);
    void caseAVarDefStmt(AVarDefStmt node);
    void caseAVarInitStmt(AVarInitStmt node);
    void caseAVarAssignStmt(AVarAssignStmt node);
    void caseAFieldAssignStmt(AFieldAssignStmt node);
    void caseACallStmt(ACallStmt node);
    void caseASelfCallStmt(ASelfCallStmt node);
    void caseAWhileStmt(AWhileStmt node);
    void caseAIfStmt(AIfStmt node);
    void caseAReturnStmt(AReturnStmt node);
    void caseAElsePart(AElsePart node);
    void caseAOrExp(AOrExp node);
    void caseASimpleExp(ASimpleExp node);
    void caseAAndConjunction(AAndConjunction node);
    void caseASimpleConjunction(ASimpleConjunction node);
    void caseAEqComparison(AEqComparison node);
    void caseANeqComparison(ANeqComparison node);
    void caseALtComparison(ALtComparison node);
    void caseAGtComparison(AGtComparison node);
    void caseALteqComparison(ALteqComparison node);
    void caseAGteqComparison(AGteqComparison node);
    void caseAIsComparison(AIsComparison node);
    void caseASimpleComparison(ASimpleComparison node);
    void caseAAddArithExp(AAddArithExp node);
    void caseASubArithExp(ASubArithExp node);
    void caseASimpleArithExp(ASimpleArithExp node);
    void caseAMulFactor(AMulFactor node);
    void caseADivFactor(ADivFactor node);
    void caseAModFactor(AModFactor node);
    void caseASimpleFactor(ASimpleFactor node);
    void caseANotLeftUnaryExp(ANotLeftUnaryExp node);
    void caseANegLeftUnaryExp(ANegLeftUnaryExp node);
    void caseASimpleLeftUnaryExp(ASimpleLeftUnaryExp node);
    void caseACallRightUnaryExp(ACallRightUnaryExp node);
    void caseAIsaRightUnaryExp(AIsaRightUnaryExp node);
    void caseAAsRightUnaryExp(AAsRightUnaryExp node);
    void caseASimpleRightUnaryExp(ASimpleRightUnaryExp node);
    void caseASelfCallTerm(ASelfCallTerm node);
    void caseAParTerm(AParTerm node);
    void caseANewTerm(ANewTerm node);
    void caseAFieldTerm(AFieldTerm node);
    void caseAVarTerm(AVarTerm node);
    void caseANumTerm(ANumTerm node);
    void caseANullTerm(ANullTerm node);
    void caseASelfTerm(ASelfTerm node);
    void caseATrueTerm(ATrueTerm node);
    void caseAFalseTerm(AFalseTerm node);
    void caseAStringTerm(AStringTerm node);
    void caseACall(ACall node);
    void caseASelfCall(ASelfCall node);
    void caseAArgs(AArgs node);
    void caseAAdditionalArg(AAdditionalArg node);
    void caseAArg(AArg node);

    void caseTClass(TClass node);
    void caseTSuper(TSuper node);
    void caseTVar(TVar node);
    void caseTFun(TFun node);
    void caseTIntern(TIntern node);
    void caseTEnd(TEnd node);
    void caseTDo(TDo node);
    void caseTWhile(TWhile node);
    void caseTIf(TIf node);
    void caseTThen(TThen node);
    void caseTElse(TElse node);
    void caseTReturn(TReturn node);
    void caseTIs(TIs node);
    void caseTIsa(TIsa node);
    void caseTAs(TAs node);
    void caseTNew(TNew node);
    void caseTNull(TNull node);
    void caseTFalse(TFalse node);
    void caseTTrue(TTrue node);
    void caseTSelf(TSelf node);
    void caseTAnd(TAnd node);
    void caseTOr(TOr node);
    void caseTNot(TNot node);
    void caseTLPar(TLPar node);
    void caseTRPar(TRPar node);
    void caseTPlus(TPlus node);
    void caseTMinus(TMinus node);
    void caseTStar(TStar node);
    void caseTSlash(TSlash node);
    void caseTPercent(TPercent node);
    void caseTEq(TEq node);
    void caseTNeq(TNeq node);
    void caseTLt(TLt node);
    void caseTGt(TGt node);
    void caseTLteq(TLteq node);
    void caseTGteq(TGteq node);
    void caseTComma(TComma node);
    void caseTColon(TColon node);
    void caseTDot(TDot node);
    void caseTId(TId node);
    void caseTClassName(TClassName node);
    void caseTFieldName(TFieldName node);
    void caseTInvalidFieldName(TInvalidFieldName node);
    void caseTNumber(TNumber node);
    void caseTInvalidNumber(TInvalidNumber node);
    void caseTString(TString node);
    void caseTInvalidString(TInvalidString node);
    void caseTEol(TEol node);
    void caseTBlank(TBlank node);
    void caseTComment(TComment node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}
