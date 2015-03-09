package de.bayerl.statistics.transformer;

import com.google.common.collect.Lists;
import de.bayerl.statistics.model.Cell;
import de.bayerl.statistics.model.Row;
import de.bayerl.statistics.model.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeleteMatchingRow extends Transformation {

    private String term;
    private int[] protectedRows;

    public DeleteMatchingRow(String term, int[] protectedRows) {
        this.term = term;
        this.protectedRows = protectedRows;
    }

    @Override
    public String getName() {
        return "deleteMatchingRow_" + term;
    }

    @Override
    protected Table transformStep(Table table) {
        List<Integer> rows = new ArrayList<>();
        // find rows that will be deleted
        for (int i = 0; i < table.getRows().size(); i++) {
            Row row = table.getRows().get(i);

            for (int y = 0; y < row.getCells().size(); y++) {
                Cell cell = row.getCells().get(y);
                // check if the current value contains the term
                if (cell.getValue().getValue().contains(term)) {
                    rows.add(i);
                    break;
                }
            }
        }

        // check for protected rows
        for (int i : protectedRows) {
            for (int y = 0; y < rows.size(); y++) {
                if (rows.get(y) == i) {
                    rows.remove(y);
                    break;
                }
            }
        }

        // prepare row list for deletion
        List<Integer> copy = Lists.newArrayList(rows);
        Collections.sort(copy);
        Collections.reverse(copy);

        // delete rows
        for (Integer i : copy) {
            DeleteRowCol deleteRowCol = new DeleteRowCol(true, i);
            table = deleteRowCol.transformStep(table);
        }

        return table;
    }
}