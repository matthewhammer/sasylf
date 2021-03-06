package edu.cmu.cs.sasylf.ast;

import edu.cmu.cs.sasylf.prover.Judgment;
import edu.cmu.cs.sasylf.prover.ProofImpl;
import edu.cmu.cs.sasylf.prover.Proof;
import edu.cmu.cs.sasylf.prover.Prover;
import edu.cmu.cs.sasylf.term.Term;
import edu.cmu.cs.sasylf.util.ErrorHandler;
import edu.cmu.cs.sasylf.util.Location;

public class DerivationBySolve extends DerivationWithArgs {
	public DerivationBySolve(String n, Location l, Clause c) {
		super(n,l,c);
	}

	@Override
	public String prettyPrintByClause() {
		return " by solve";
	}

	@Override
	public void typecheck(Context ctx) {
		super.typecheck(ctx);

		Prover prover = new Prover();
		Term term = getElement().asTerm().substitute(ctx.currentSub);
		edu.cmu.cs.sasylf.ast.Judgment type = (edu.cmu.cs.sasylf.ast.Judgment)((ClauseUse)getClause()).getConstructor().getType();
		Judgment judgment = new Judgment(term, type);
		Proof partial = new ProofImpl(judgment);
		Proof complete = prover.prove(partial, 5);

		if (complete == null)
			ErrorHandler.report("Unable to find proof", this);
		else {
			complete.prettyPrint();
			System.out.println();
			ErrorHandler.warning("proof by solve is not reliable", this);
		}
	}
}
