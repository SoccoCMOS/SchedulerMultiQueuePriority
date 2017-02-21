import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import DonneesStatiques.DataDG;
import DonneesStatiques.State;
import DonneesStatiques.TypeEtat;

public class GanttDemo extends ApplicationFrame {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	JFreeChart jfreechart;
	public Color pret= new Color(0, 204, 203);
	public Color Actif= new Color(16, 52, 166);
	public Color Inexistant= new Color(44, 117,255);
	public Color  ES0 = new Color(223, 242, 255);
	public Color  ES1 =new Color(159, 36, 150);
	public Color  ES2= new Color(153, 153, 255);
	public Color  ES3 = new Color(255, 204, 102);
	public Color AttenteES =  new Color (255,128,0);
	

	
	/*(0, new Color(0, 204, 203)); // Bleu dragée
	(1, new Color(223, 242, 255)); // Bleu égyptien
	(2, new Color(16, 52, 166)); // Bleu électrique
	(3, new Color(44, 117, 255)); // light light purple
	(4, new Color(159, 36, 150)); // cyan
	(5, new Color(153, 153, 255)); // light blue
	(6, new Color(255, 204, 102)); // yellow
	(7, new Color (255,128,0));//orange */
	
	public JFreeChart getJfreechart() {
		return jfreechart;
	}

	public void setJfreechart(JFreeChart jfreechart) {
		this.jfreechart = jfreechart;
	}

	static TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();

	public GanttDemo(String s, DataDG[] d, int nbProcessus) {
		super(s);
		JPanel jpanel = createDemoPanel(d, nbProcessus);

		jpanel.setPreferredSize(new Dimension(700, 400));
		setContentPane(jpanel);
	}

	private static JFreeChart createChart(IntervalCategoryDataset dataset) {
		final JFreeChart chart = GanttChartFactory.createGanttChart(
				"Diagramme de Gantt", "processus", "	Temps en (ms)", dataset,
				true, true, false);

		return chart;
	}

	private static IntervalCategoryDataset createDataset(DataDG[] dg, int nb) {
		TaskSeries taskseries = new TaskSeries("");
         String s ;
		/********************** recup DATAGantt ******/

		for (int i = 0; i < nb; i++) {

			TaskNumeric[] tasktab = new TaskNumeric[50];
			TaskNumeric task = TaskNumeric.duration("p" + i, 0, 300,
					TypeEtat.Inexistant.toString());
			int j = 0;

			for (State p : dg[i].getProgress()) {

				if( p.getEtat().toString().equals("ES")){ s=(p.getEtat().toString()+p.getNumPeriph());
				}  else s=p.getEtat().toString();
			
				System.out.println(s);
				tasktab[j] = TaskNumeric.duration("", p.getDateDebut(),p.getdateReveil() - p.getDateDebut(), s);
				task.addSubtask(tasktab[j]);

				j++;
			}

			task.setPercentComplete(0.0D);

			taskseries.add(task);

		}

		taskseriescollection.add(taskseries);

		return taskseriescollection;
	}

	public JPanel createDemoPanel(DataDG[] d, int nb) {

		this.jfreechart = createChart(createDataset(d, nb));

		this.jfreechart.removeLegend();
		LegendTitle legend = new LegendTitle(new LineLegendItemSource());
		this.jfreechart.addLegend(legend);

		
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setMouseWheelEnabled(true);
		return chartpanel;

	}

	class LineLegendItemSource implements LegendItemSource {
		public LegendItemCollection getLegendItems() {
			LegendItemCollection itemCollection = new LegendItemCollection();

			LegendItem item = new LegendItem("Pret", pret);
			
			LegendItem item1 = new LegendItem("Actif",Actif);
			LegendItem item2 = new LegendItem("Inexistant", Inexistant);
			LegendItem item3 = new LegendItem("ES0",ES0);
			LegendItem item4= new LegendItem("ES1", ES1);
			LegendItem item5 = new LegendItem("ES2",ES2);
			LegendItem item6= new LegendItem("ES3", ES3);
			LegendItem item7= new LegendItem("AttenteES", AttenteES);
			
			itemCollection.add(item);
			/*LegendItem item0 = new LegendItem("ES", new Color(223, 242, 255));*/
		
			itemCollection.add(item1);
			itemCollection.add(item2);
	        itemCollection.add(item3);
	    	itemCollection.add(item4);
	    	itemCollection.add(item5);
	    	itemCollection.add(item6);
	    	itemCollection.add(item7);
			return itemCollection;
		}
	}

	
}
