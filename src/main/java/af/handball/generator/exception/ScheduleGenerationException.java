package af.handball.generator.exception;

public class ScheduleGenerationException extends Exception {
	public ScheduleGenerationException() {
        this("ScheduleGenerationException");
    }

	public ScheduleGenerationException(String msg) {
		super(msg);
	}
}
