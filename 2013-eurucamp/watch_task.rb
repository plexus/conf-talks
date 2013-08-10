require 'rb-inotify'

class WatchTask
  def initialize(name, files, &blk)
    Rake::Task.define_task name do
      Array(files).each do |pattern|
        notifier.watch(pattern, :modify, &blk)
        at_exit { blk.call ; notifier.run }
      end
    end
  end

  def notifier
    @notifier ||= INotify::Notifier.new
  end
end

def watch(files, name = :watch, &blk)
  WatchTask.new(name, files, &blk)
end
