import java.awt.Color;
import java.awt.Paint;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.IntervalCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

public class GanttChartFactory extends ChartFactory {

	protected static ChartTheme currentTheme = new StandardChartTheme("JFree");

	public static JFreeChart createGanttChart(String title,
			String categoryAxisLabel, String valueAxisLabel,
			IntervalCategoryDataset dataset, boolean legend, boolean tooltips,
			boolean urls) {

		CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
		ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

		CategoryItemRenderer renderer = new MyGanttRenderer(
				GanttDemo.taskseriescollection);

		if (tooltips) {
			renderer.setBaseToolTipGenerator((CategoryToolTipGenerator) new IntervalCategoryToolTipGenerator(
					"{1}: {3} - {4}", NumberFormat.getNumberInstance()));
		}
		if (urls) {
			renderer.setBaseItemURLGenerator(new StandardCategoryURLGenerator());

		}

		CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
				renderer);
		plot.setOrientation(PlotOrientation.HORIZONTAL);

		JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
				plot, legend);
		currentTheme.apply(chart);
		return chart;
	}

	private static class MyGanttRenderer extends GanttRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// private static final int PASS = 2; // assumes two passes
		private final List<Color> clut = new ArrayList<Color>();
		private final TaskSeriesCollection model;

		private int row;
		private int col;
		private int index = 0;
		static int indice;

		public MyGanttRenderer(TaskSeriesCollection model) {
			this.model = model;
			indice = 0;
			// initialize clut
			clut.add(0, new Color(0, 204, 203)); // Bleu dragée

			clut.add(1, new Color(223, 242, 255)); // Bleu égyptien
			clut.add(2, new Color(16, 52, 166)); // Bleu électrique
			clut.add(3, new Color(44, 117, 255)); // light light purple
			clut.add(4, new Color(159, 36, 150)); // cyan
			clut.add(5, new Color(153, 153, 255)); // light blue
			clut.add(6, new Color(255, 204, 102)); // yellow
			clut.add(7, new Color (255,128,0));//orange 
		
	}
		@Override
		public Paint getItemPaint(int row, int col) {

			TaskSeries series = (TaskSeries) model.getRowKeys().get(row);
			@SuppressWarnings("unchecked")
			List<TaskNumeric> tasks = series.getTasks(); // unchecked

			if (this.row == row && this.col == col) {
				

				if (indice < tasks.get(col).getSubtaskCount())
					if (((TaskNumeric) tasks.get(col).getSubtask(indice))
							.getSt().toString().equals("Pret")) {
						index = 0;
					}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("ES0")) {
					index = 1;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("Actif")) {
					index = 2;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("Inexistant")) {
					index = 3;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("ES1")) {
					index = 4;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("ES2")) {
					index = 5;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("ES3")) {
					index = 6;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("AttenteES")) {
					index = 7;
				}
			
				indice++;
			} else {
				indice = 0;
				this.row = row;
				this.col = col;

			

				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("ES0")) {
					index = 1;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("Actif")) {
					index = 2;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("Inexistant")) {
					index = 3;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("ES1")) {
					index = 4;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("ES2")) {
					index = 5;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("ES3")) {
					index = 6;
				}
				if (((TaskNumeric) tasks.get(col).getSubtask(indice)).getSt()
						.toString().equals("AttenteES")) {
					index = 7;
				}

				indice++;

			}

			return clut.get(index);
		}
	}
}