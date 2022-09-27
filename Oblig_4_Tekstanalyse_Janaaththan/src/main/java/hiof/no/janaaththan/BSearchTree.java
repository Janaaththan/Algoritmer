package hiof.no.janaaththan;

public class BSearchTree {

    //Rotene av binære treet
    TreeNode root;


    //Danner et tomt tre
    public BSearchTree() {
        root = null;
    }

    //Ord Innsettingsfunksjon for det binære treet hvor rooten blir inkludert som den nåværende noden, og bygges videre oppå
    public void insert(String word) {
        root = insert(root, word);
    }

    //Funksjon som setter inn ordet ut ifra alfabetisk rekkfølge og gjentagelse inn i treet
    private TreeNode insert(TreeNode currNode, String word) {
        if (currNode == null) {
            currNode = new TreeNode(word);
        }
        //Setter venstresiden dersom ordets forbokstav er mindre enn den nåværende noden
        else if ( word.compareTo( currNode.getWord()) < 0 ){
            currNode.setLeftChild( insert(currNode.getLeftChild(), word) );
        }
        //Dersom ordet er lik vil vi bare øke antallet på den noden
        else if ( word.compareTo( currNode.getWord()) == 0 ) {
            currNode.increaseCounter();
        }
        //Dersom ordet ikke dekker argumentene ovenfor, vil den ha en "større" bokstav; altså lenger ned alfabetet
        else {
            currNode.setRightChild( insert(currNode.getRightChild(), word) );
        }

        return currNode;
    }

    public void printInorder() {
        printInorder(root);
    }

    private void printInorder(TreeNode currNode) {
        //Sjekker om det er den nåværende noden er tom
        if (currNode == null) {
            return;
        }


        printInorder(currNode.getLeftChild());
        System.out.println(currNode);
        printInorder(currNode.getRightChild());
    }

}